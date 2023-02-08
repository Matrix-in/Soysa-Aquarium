package lk.matrix.soysaaquarium.Controller;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class desktopNotificationController {
    public void notification(String text,String title) {
        // Image image =new Image("/Assets/fishTick.png");
        Notifications notifications=Notifications.create();
        //notifications.graphic(new ImageView(image));
        notifications.text(text);
        notifications.title(title);
        notifications.hideAfter(Duration.seconds(5));
        //notifications.darkStyle();
        notifications.show();
        notifications.position(Pos.TOP_RIGHT);
    }
}
