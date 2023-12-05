import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.shell.JobEntryShell;
import org.pentaho.di.job.entries.trans.JobEntryTrans;
import org.pentaho.di.job.entry.JobEntryCopy;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

/**
 * Java API
 * <a href="https://blog.51cto.com/u_16175476/7215113">Java 调用 Kettle</a>
 * <a href="https://blog.csdn.net/lizhiqiang1217/article/details/90027277">Java 调用 Kettle</a>
 * <a href="https://github.com/pentaho/pdi-sdk-plugins/blob/master/kettle-sdk-embedding-samples/src/main/java/org/pentaho/di/sdk/samples/embedding/RunningJobs.java">RunningJobs.java</a>
 * <a href="https://github.com/pentaho/pdi-sdk-plugins/blob/master/kettle-sdk-embedding-samples/src/main/java/org/pentaho/di/sdk/samples/embedding/RunningTransformations.java">RunningTransformations.java</a>
 */
public class Main {

    public static void main(String[] args) throws KettleException {
        // 初始化 Kettle
        KettleEnvironment.init();
        // 创建 TransMeta 对象
        String filename = ClassLoader.getSystemResource("trans1.ktr").getFile();
        TransMeta meta = new TransMeta(filename);
        // 创建 Trans 对象
        Trans trans = new Trans(meta);
        // 执行 Trans
        trans.prepareExecution(null); // execute(null)
        trans.startThreads();
        trans.waitUntilFinished();
        // 执行结果
        if (trans.getErrors() == 0) {
            System.out.println("执行成功！");
        } else {
            System.out.println("执行失败！");
        }
    }

    /**
     * Creates a Transformation
     */
    public static TransMeta createTrans() {
        // Input Step
        StepMeta inputStep = new StepMeta("Input", new TableInputMeta());
        inputStep.setLocation(100, 100);
        inputStep.setDraw(true);

        // Output Step
        StepMeta outputStep = new StepMeta("Output", new TableOutputMeta());
        outputStep.setLocation(300, 100);
        outputStep.setDraw(true);

        // 定义 Step 之间的连接
        TransHopMeta hop = new TransHopMeta(inputStep, outputStep);

        // 创建 TransMeta 对象，添加两个 Step 并添加连线
        TransMeta transMeta = new TransMeta();
        transMeta.setName("MyTrans");
        transMeta.addStep(inputStep);
        transMeta.addStep(outputStep);
        transMeta.addTransHop(hop);

        return transMeta;
    }

    /**
     * Creates a Job
     */
    public static Job createJob() {
        // 创建作业对象
        JobMeta jobMeta = new JobMeta();

        // 设置作业的名称
        jobMeta.setName("MyJob");

        // 创建一个转换节点
        TransMeta transMeta = createTrans();
        JobEntryTrans trans = new JobEntryTrans("MyTransformation");
        JobEntryCopy transJobEntry = new JobEntryCopy(trans);
        transJobEntry.setEntry(trans);
        jobMeta.addJobEntry(transJobEntry);

        // 创建一个 Shell 脚本节点
        JobEntryCopy scriptJobEntry = new JobEntryCopy(new JobEntryShell("MyShell"));
        scriptJobEntry.setEntry(new JobEntryShell());
        jobMeta.addJobEntry(scriptJobEntry);

        // 创建节点之间的连接
        jobMeta.addJobHop(new JobHopMeta(transJobEntry, scriptJobEntry));

        // 创建作业对象
        Job job = new Job(null, jobMeta);

        return job;
    }
}
