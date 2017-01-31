package james.tooltipssample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import james.tooltips.Tooltip;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Tooltip(this)
                .setText(getString(R.string.cabbages))
                .attachTo(findViewById(R.id.tooltip));

        new Tooltip(this)
                .setText(getString(R.string.cabbages))
                .setPosition(Tooltip.Position.ABOVE)
                .setBackgroundColor(Color.BLUE)
                .attachTo(findViewById(R.id.tooltipAbove));

        new Tooltip(this)
                .setText(getString(R.string.cabbages))
                .setPosition(Tooltip.Position.LEFT)
                .setTextColor(Color.GREEN)
                .attachTo(findViewById(R.id.tooltipLeft));

        new Tooltip(this)
                .setText(getString(R.string.cabbages))
                .setPosition(Tooltip.Position.RIGHT)
                .attachTo(findViewById(R.id.tooltipRight));

        new Tooltip(this)
                .setText(getString(R.string.cabbages))
                .setPosition(Tooltip.Position.CENTER)
                .attachTo(findViewById(R.id.tooltipCenter));
    }
}
