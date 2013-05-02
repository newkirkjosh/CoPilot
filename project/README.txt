Instructions for compiling, building, and running CoPilot

Install the following libraries:

	ActionBarSherlock
		- Download at https://github.com/JakeWharton/ActionBarSherlock.git
		- Put into workspace

	SlidingMenuLibrary:
		- Downlaod at https://github.com/jfeinstein10/SlidingMenu.git
		- Put into workspace
		- Open properties for project and add a reference to the ActionBarSherlock
		
	Google Play Services Library:
		- Update to the most recent version of your Google Play Services from the Android SDK Manager
		- Import the library from where inside your Android folder located on your computer

Do this for all of the libraries and the application:

	Right click on the project > Android Tools > Add Support Library
	
Add SlidingMenuLibrary and Google Play Services Library to the project as references by:
	Right Click on project > Properties > Android > Add
	
Clean all projects.
Run on the device you currently would like to install onto.
Device must be Ice Cream Sandwich API 4.0.2 or later.