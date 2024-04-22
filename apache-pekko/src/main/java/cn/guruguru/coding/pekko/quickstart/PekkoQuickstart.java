package cn.guruguru.coding.pekko.quickstart;

import org.apache.pekko.actor.typed.ActorSystem;
import java.io.IOException;

/**
 * Apache Pekko Quickstart
 *
 * <p>运行： `mvn compile exec:exec`
 * @see <a href="https://github.com/apache/pekko-quickstart-java.g8">Apache Pekko Quickstart with Java</a>
 * https://github.com/guobinhit/akka-guide/blob/master/articles/qucikstart-akka-java.md
 * https://developer.lightbend.com/start/?group=akka&project=akka-quickstart-java
 */
public class PekkoQuickstart {

    public static void main(String[] args) {
        //#actor-system
        final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "hellopekko");
        //#actor-system

        //#main-send-messages
        greeterMain.tell(new GreeterMain.SayHello("Charles"));
        //#main-send-messages

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            greeterMain.terminate();
        }
    }

}