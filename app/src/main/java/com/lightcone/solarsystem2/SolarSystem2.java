package com.lightcone.solarsystem2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;

public class SolarSystem2 extends Activity implements OnClickListener {
	KeplerRunner2 krunner;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* In the following we lay out the screen entirely in code (main.xml isn't used).  We
            wish to lay out a stage for planetary motion and five buttons at the bottom using
            LinearLayout.  The instance krunner of KeplerRunner is added to a LinearLayout LL1
            using addView.  Then the buttons are added 5 across in a LinearLayout LL2 using
            addView, and finally we use addView to add LL2 to LL1 and then use setContent
            to set the content view to LL1 and its children. The formatting of these layouts is
            controlled using the LinearLayout.LayoutParams lp1 and lp2. */
        
        /* First define some LayoutParams that we will use to control layout format.  Note
            that we use the LinearLayout.LayoutParams(int width, int height, float weight) form
            of the constructor, which allows us to assign weights (corresponding to the XML
            attribute android:layout_weight).  Assigning weights is essential to distributing
            screen space among the View and 5 buttons. (We also could have used the
            LinearLayout.LayoutParams(int width, int height) form of the constructor and then
            populated the public weight field with lp1.weight = float, etc.) */ 
        
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 20);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
        		LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 80);
        
        LinearLayout LL1 = new LinearLayout(this);
        LL1.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout LL2 = new LinearLayout(this);
        LL2.setOrientation(LinearLayout.HORIZONTAL);
        
        // Instantiate the class MotionRunner to define the entry screen display
        krunner = new KeplerRunner2(this);
        krunner.setLayoutParams(lp2);
        
        krunner.setBackgroundColor(Color.BLACK);
        LL1.addView(krunner);
        
        // Add five buttons to the bottom of the screen in the LinearLayout LL2 with 
        // LinearLayout.LayoutParams lp1.  Since the layout orientation of LL2 was set to 
        // LinearLayout.HORIZONTAL above and the weight of lp1 was set to 20 in its
        // constructor, this will lay the five buttons out horizontally with equal spacing.
        
        Button button1 = new Button(this);
        button1.setLayoutParams(lp1);
        button1.setText("fast");
        button1.setId(1);
        button1.setOnClickListener(this);
        LL2.addView(button1);

        Button button2 = new Button(this);
        button2.setLayoutParams(lp1);
        button2.setText("slow");
        button2.setId(2);
        button2.setOnClickListener(this);
        LL2.addView(button2);
        
        Button button3 = new Button(this);
        button3.setLayoutParams(lp1);
        button3.setText("big");
        button3.setId(3);
        button3.setOnClickListener(this);
        LL2.addView(button3);
        
        Button button4 = new Button(this);
        button4.setLayoutParams(lp1);
        button4.setText("small");
        button4.setId(4);
        button4.setOnClickListener(this);
        LL2.addView(button4);
        
        Button button5 = new Button(this);
        button5.setLayoutParams(lp1);
        button5.setText("label");
        button5.setId(5);
        button5.setOnClickListener(this);
        LL2.addView(button5);
        
        LL2.setBackgroundColor(Color.BLACK);
        
        // Now add the five buttons to the bottom of the LinearLayout LL1 that already contains
        // the animation view defined by krunner, and finally set the content view for the screen 
        // to the LinearLayout LL1.
        
        LL1.addView(LL2);
        setContentView(LL1);
    }
    
    @Override
	public void onPause() {
		super.onPause();
		// Stop animation loop if going into background
		krunner.stopLooper();
    }
    
    @Override
	public void onResume() {
		super.onResume();
		// Resume animation loop
		krunner.startLooper();
    }

    // Process the button clicks
	@Override
	public void onClick(View v) {
		
		double delayScaler = 1.2;
		double zoomScaler = 1.1;
		
		switch(v.getId()){
		
			case 1:
				long test = krunner.setDelay(1/delayScaler);    // Run faster
				if(test == -2){
					Toast.makeText(this, "Maximum speed. Can't increase further", 
							Toast.LENGTH_SHORT).show();
				}
				break;
			
			case 2:
				krunner.setDelay(delayScaler);       // Run slower
				break;
			
			case 3:
				krunner.setZoom(zoomScaler);      // Zoom in
				break;
				
			case 4:
				krunner.setZoom(1/zoomScaler);   // Zoom out
				break;
				
			case 5:
				krunner.showLabels = !krunner.showLabels;   // Toggle planet labels
				break;	

		}	
	}  
}