import controller.Controller;
import view.View;

public class app {
	public static void main(String arg[]) {
		Controller controller = new Controller(new View());
		controller.newGame();
	}
}
