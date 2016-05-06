# Frequently Asked Questions #

[Where are the current list of functions supported?](FAQ#Where-are-the-current-list-of-functions-supported?.md)

[How do I create and run an .m file?](FAQ#How-do-I-create-and-run-a-.m-file?.md)

[How do I setup to be a developer?](FAQ#How-do-I-setup-to-be-a-developer?.md)

[Why spend time developing open source, free, educational SW?](FAQ#Why-spend-time-developing-open-source,-free,-educational-SW?.md)

> ## Where are the current list of functions supported? ##
    1. Current functions supported can be found [here](http://www.jmathlib.de/docs/handbook/index.php) (ignore plotting functions)

> ## How do I create and run a .m file? ##
    1. Create .m file (do 1, 2 or 3)
      1. Use another app to create a .m file with the script/function you want to use.
      1. Or load the .m onto your sdcard or device.
      1. In Addi, type _ed /path/filename.m_ example _ed /sdcard/test/m_
    1. Go to location of .m file in Addi
      1. _cd locationofyourmfile_ maybe this is _cd /sdcard_
    1. Run it (do 1, 2 or 3) in Addi
      1. if it is a script: _nameoffilewithoutdotm_
      1. if it is a script and you used _ed_ to create it, you can hit the save and run button.
      1. if it is a function: _return = nameoffilewithoutdotm(arg1, arg2, etc)_

> ## How do I setup to be a developer? ##
    1. Follow the Android [Hello World](http://developer.android.com/resources/tutorials/hello-world.html) instructions for setup of your PC.
      1. Follow each step fully.
      1. We develop using eclipse.
      1. You can check if you are setup correctly by trying to create and run the Hello World example.
    1. Get subclipse
      1. In Eclipse click help->install new software
      1. Click add
      1. Use this website http://subclipse.tigris.org/update_1.6.x
      1. Check all three boxes
      1. Install them (I have had this fail before and had to do it twice)
    1. Create an Addi project
      1. In Eclipse click file -> new project -> svn -> create new repository location -> https://addi.googlecode.com/svn
      1. Click on addi

> ## Why spend time developing open source, free, educational SW? ##
> > There are many ways to make the World a better place.  Each person should put their talents towards doing so.  Whether your morals are defined by religion or Earthly experiences or some combination thereof, I think most people would agree that with this sentiment.  Creating free educational software lowers the cost of education (formal or self education).  Making it open source and under the GNU license means it will be free forever.  It also gives it permanence.  If we drop the ball and stop developing this code, someone else can keep the banner going forward.  Making it open source, also serves as an educational tool to anyone wanting to create something similar (even if that is a competing idea).  I, myself, Corbin Champion (not all developers will share my religious perspective), think of it as a form of tithing (Biblical reference).  I am giving my resources, in this case mostly time, because it is fun and interesting to me, but also because I want to show love to the World.  If improves anyone's life, I have received enough payment.