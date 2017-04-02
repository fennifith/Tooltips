# Tooltips
**Note: Development of Tooltips has been discontinued as they will be [added in Android O](https://developer.android.com/reference/android/view/View.html#attr_android:tooltipText).**

A small tooltips library for Android based on Material Design.

For testing and experimentation purposes, a sample apk can be downloaded [here](https://github.com/TheAndroidMaster/Tooltips/releases).

## Setup
The Gradle dependency is available through jcenter, which is used by default in Android Studio. To add the module to your project, copy this line into the dependencies section of your build.gradle file.

``` gradle
compile 'james.tooltips:tooltips:0.0.4'
```

## Usage

### Basic Tooltip
To create a basic tooltip, simply add the java snippet below into your project.
``` java
new Tooltip(this) //pass either an activity or the root view of a dialog, fragment, or recyclerview item
  .setText("Hi!") //the text to be displayed in the tooltip
  .attachTo(view); //the view you want the tooltip to be shown under
```

When the view is pressed, the tooltip will show. When the finger is released, the tooltip will hide again. Keep in mind that this method sets the OnTouchListener of the view, so it should not be changed after the tooltip is attached.

### Manual Showing/Dismissing
If you want more functionality, sometimes it is better to show and dismiss the tooltip manually. An example of this is below.
``` java
Tooltip tooltip = new Tooltip(this)
  .setText("Hi!");
  
tooltip.showFor(view); //show the tooltip under the specified view
```

When you want the tooltip to be dismissed, simply call `tooltip.dismiss();`.

### Show For Coordinates
If there isn't any specific view you want to show the tooltip under, or if you want the tooltip to be shown at a specific part of a view, it is easier to pass x and y coordinates of where you want the tooltip to be shown instead of a view. An example of this is below.
``` java
Tooltip tooltip = new Tooltip(this)
  .setText("Hi!");
  
tooltip.showFor(x, y); //show the tooltip under the specified coordinates
```

Again, simply call `tooltip.dismiss();` when you want it to be dismissed.

### Customization
It is also possible to add an icon, change the position, padding, background shape, background color, icon color, and text color of the tooltip.

#### Icon
To add an icon, pass a drawable to the tooltip using the `Tooltip.setIcon(Drawable)` method. To hide the icon after one has been set, simply call `Tooltip.setIcon(null)`.

#### Position
There are five available positions that the tooltip can be shown in: `Tooltip.Position.ABOVE`, `Tooltip.Position.BELOW`, `Tooltip.Position.LEFT`, `Tooltip.Position.RIGHT`, and `Tooltip.Position.CENTER`. These can be changed using the `Tooltip.setPosition(Position)` method like below.
``` java
new Tooltip(this)
  .setText("Hi!")
  .setPosition(Tooltip.Position.ABOVE)
  .attachTo(view);
```

#### Padding
By default, the tooltip will add 16dp padding between it and the view (or position) it is attached to, **unless** the position is set to `Tooltip.Position.CENTER`. The padding can be changed using the `Tooltip.setPadding(int)` method like below. Keep in mind the padding is measured in pixels. To convert between pixels and dp, use the `ViewUtils.pxToDp(int)` and `ViewUtils.dpToPx(int)` methods.
``` java
new Tooltip(this)
  .setText("Hi!")
  .setPadding(ViewUtils.dpToPx(6))
  .attachTo(view);
```

#### Background Shape
A custom drawable can be set as the tooltip background using the `Tooltip.setBackground(Drawable)` method like below.
``` java
new Tooltip(this)
  .setText("Hi!")
  .setBackground(drawable)
  .attachTo(view);
```

#### Background Color
The background color can be set using the `Tooltip.setBackgroundColor(int)` method. Keep in mind that since this method tints the background drawable, it must be called *after* `Tooltip.setBackground(Drawable)` if you are using a custom drawable.
``` java
new Tooltip(this)
  .setText("Hi!")
  .setBackgroundColor(Color.BLUE)
  .attachTo(view);
```

#### Icon Color
The icon color can be changed using the `Tooltip.setIconTint(int, PorterDuff.Mode)` method like below.
``` java
new Tooltip(this)
  .setText("Hi!")
  .setIcon(drawable)
  .setIconTint(Color.BLUE, PorterDuff.Mode.SRC_IN)
  .attachTo(view);
```

#### Text Color
The text color can be modified using the `Tooltip.setTextColor(int)` method like below.
``` java
new Tooltip(this)
  .setText("Hi!")
  .setBackgroundColor(Color.WHITE)
  .setTextColor(Color.BLACK)
  .attachTo(view);
```
