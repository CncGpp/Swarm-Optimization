package view;

import javafx.animation.Transition;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class HorizontalCollapse extends Transition {
    protected Region region;
    protected double startWidth;
    protected double newWidth;
    protected double widthtDiff;


    public HorizontalCollapse(Duration duration, Region region, double newWidth ) {
        setCycleDuration(duration);
        this.region = region;
        this.newWidth = newWidth;
        this.startWidth = region.getWidth();
        this.widthtDiff = newWidth - startWidth;
    }

	@Override
	protected void interpolate(double frac) {
		 region.setMinWidth( startWidth + ( widthtDiff * frac ) );
		 region.setPrefWidth( startWidth + ( widthtDiff * frac ) );
		 region.setMaxWidth( startWidth + ( widthtDiff * frac ) );
		 System.out.println("" + frac);
	}

}
