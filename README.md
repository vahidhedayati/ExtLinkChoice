ExtLinkChoice
=============

Grails plugin which gives your app easy ways of either giving the end user or setting the end user External Link choice to either use Modal to show the link in a popup window, same window, new tab. There is a small menu that can be added to your main.gsp or where required to let user change their choice. The choice is stored in session and so long as further link calls use the same method all links will then open according to set value. There is a little configuration required on your own app and I will provide a sample app as well as try walk through it as best as I can


Although the modalbox plugin exists on grails.org, the plugin is bound to internal objects within a grails application, attempts to call external sites are blocked, I think its some security definition within ajax to stop cross site injection {for hacking reasons it has been disabled}.



Please refer to https://github.com/vahidhedayati/ExtLinkChoiceExample/ which has a sample site, to explain what needs to be done now:




#/ grails-app / conf / ApplicationResources.groovy

	    modal { resource url: 'js/bootstrap.min.js' }



The above bootstrap.min.js exists in this plugin so there is no need to get this js file, simply define the modal call and refer to it in layout main.gsp include



#/ grails-app / views / layouts / main.gsp 

	    <g:javascript library="modal"/>
      <g:javascript library="jquery"/>
  


#/ grails-app / controllers / gtest / LinkController.groovy
Create a Controller it is called LinkController in this example:

    package gtest
    class LinkController {
      def index() { }
    }




#/ grails-app / views / link / index.gsp
    
    <!DOCTYPE html> <html> <head>
    <meta name="layout" content="main"> <g:set var="entityName"
    value="${message(code: 'gtest.label', default: 'gtest')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title> </head>
    <body>

    
       <!--  method 1 - do not use with method2 in example2.gsp right at the bottom -->
    	<!-- both method1 and method2 called together breaks the functionality of this plugin -->
   	<!-- this returns the user choice values -->
	<extlink:userPref update="linkPanel"/>
	
 	<!-- This bit gives the show/hide choices for above return -->
	<extlink:userPref update="linkPanel" updateShow="linkChooser"/>

	<a href="#" id="linkChooser">Link Choice Chooser</a>
	<div id=linkPanel name=linkPanel >
	</div>
	<br/>
	
	<!-- end method 1 call --> 
	<!-- below are all the links which works with either method1 or method2 call -->

	<extlink:returnLink link="http://www.grails.org" description="Grails Site"  />

	<extlink:returnLink link="http://www.grails.info" description="Grails INFO Site" />

    	<!-- in this example we are calling a link called grails, gives a description defines the modalLabel which is found further down and resoveIt trys to resolve happy -->
    	<extlink:returnLink link="http://grails" description="happy site" resolveit="1"/>
    	
    	<!-- in this example we are calling a link called grails.org, gives a description defines the modalLabel which is found further down -->
    	<extlink:returnLink link="http://grails.org" description="happy site"/>
    	<!-- ALL OF THE BELOW NOW RELATED TO MODAL -->
   

	<!-- Important this is required to return all the modal footer tags scripts -->
	<extlink:modalFooter/>

	
    	</body>
    	</html>
    
    
    
    
#CSS stuff 

ExtLinkChoiceExample / web-app / css / main.css, these are additional stuff you need to add to your site, I have presented it open i.e. for you to define it, in order to give you the maximum flexibility of how you want things to look.

The first userspot stuff does the drop down menu next to a link, the link choose is for the link choice menu presented at the top and finally a larger block all related to displaying the pop up modal window


    #user_spot {
      display: inline-block;
      text-decoration: none;	
      z-index:5;
      padding:0;
 
    }
    ul#user_spot li {
    list-style-type:none;
       float:left;
        position:relative;
        padding: 5px;
        text-decoration: none;	
        margin-left: -5px;
    }
    ul#user_spot li a{
      text-decoration: none;
    }
    ul#user_spot_links {
      list-style-type:none;
      position:absolute;
      top:28px;
      display:none;
      z-index: 6;
    }
    ul#user_spot_links li {
      list-style-type:none;
      float: left;
      clear:both;
      padding: 5px;
      display: inline-block;
      position: relative;
      background-color: #aaa;
      border: 1px solid #999;
      white-space: nowrap;
      z-index: 7;
     text-decoration: none;	
    }
    ul#user_spot_links li a{
      text-decoration: none;	
    }
    ul#user_spot:hover ul#user_spot_links {
        display:block;
    }







    .linkChooser {
      display:none;
    }








	.linkchoicebutton {

	border: 1px solid rgba(0,0,0,0.2);
	box-sizing: content-box !important;
	color: #333;
	width: auto;
	
	display: inline-block;
	padding: 6px 10px;
	text-align: center;
	text-shadow: 0 0 5px rgba(0,0,0,0.2), 0 0 1px rgba(0,0,0,0.4);
	text-decoration: none;
	font-size: 12px;
	font-family: 'Calibri';
	white-space: normal;
	outline: none; 

	/* Transition */
	-moz-transition: all 200ms ease 0ms !important;
	-o-transition: all 200ms ease 0ms !important;
	-webkit-transition: all 200ms ease 0ms !important;

	/* Background Color */
	background: none repeat scroll 0 0 rgba(255,255,255,0.04);

	/* Border Rounding */
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;

	/* Background Clipping */
	-moz-background-clip: padding;
	-webkit-background-clip: padding;
	background-clip: padding-box;
	
	/* Shadow */
	box-shadow: 0 0 3px rgba(255,255,255,0.25) inset, 0 0 1px rgba(255,255,255,0.2), 0 10px 10px rgba(255,255,255,0.08) inset;
	-moz-box-shadow: 0 0 3px rgba(255,255,255,0.25) inset, 0 0 1px rgba(255,255,255,0.2), 0 10px 10px rgba(255,255,255,0.08) inset;
	-webkit-box-shadow: 0 0 3px rgba(255,255,255,0.25) inset, 0 0 1px rgba(255,255,255,0.2), 0 10x 10px rgba(255,255,255,0.08) inset;
	box-shadow: 0 0 3px rgba(255,255,255,0.25) inset, 0 0 1px rgba(255,255,255,0.2), 0 10px 10px rgba(255,255,255,0.08) inset;
	}





    .close{float:right;font-size:20px;font-weight:bold;line-height:20px;color:#000000;text-shadow:0 1px 0 #ffffff;opacity:0.2;filter:alpha(opacity=20);}.close:hover{color:#000000;text-decoration:none;opacity:0.4;filter:alpha(opacity=40);}
    button.close{padding:0;background:transparent;border:0;-webkit-appearance:none;}
    .pull-right{float:right;}
    .pull-left{float:left;}
    .hide{display:none;}
    .show{display:block;}
    .invisible{visibility:hidden;}
    .affix{position:fixed;}
    .fade{opacity:0;-webkit-transition:opacity 0.15s linear;-moz-transition:opacity 0.15s linear;-o-transition:opacity 0.15s linear;transition:opacity 0.15s linear;}.fade.in{opacity:1;}
    .collapse{position:relative;height:0;overflow:hidden;overflow:visible \9;-webkit-transition:height 0.35s ease;-moz-transition:height 0.35s ease;-o-transition:height 0.35s ease;transition:height 0.35s ease;}.collapse.in{height:auto;}
    .hidden{display:none;visibility:hidden;}
    .visible-phone{display:none !important;}
    .visible-tablet{display:none !important;}
    .hidden-desktop{display:none !important;}
    .visible-desktop{display:inherit !important;}
    .modal-open .dropdown-menu{z-index:2050;}
    .modal-open .dropdown.open{*z-index:2050;}
    .modal-open .popover{z-index:2060;}
    .modal-open .tooltip{z-index:2080;}
    .modal-backdrop{position:fixed;top:0;right:0;bottom:0;left:0;z-index:1040;background-color:#000000;}.modal-backdrop.fade{opacity:0;}
    .modal-backdrop,.modal-backdrop.fade.in{opacity:0.8;filter:alpha(opacity=80);}
    .modal{
      position:fixed;top:50%;left:50%;z-index:1050;overflow:auto;width:65em;
      margin:-250px 0 0 -280px;background-color:#ffffff;border:1px solid #999;
      border:1px solid rgba(0, 0, 0, 0.3);*border:1px solid #999;-webkit-border-radius:6px;
      -moz-border-radius:6px;border-radius:6px;
      -webkit-box-shadow:0 3px 7px rgba(0, 0, 0, 0.3);
      -moz-box-shadow:0 3px 7px rgba(0, 0, 0, 0.3);box-shadow:0 3px 7px rgba(0, 0, 0, 0.3);
      -webkit-background-clip:padding-box;
      -moz-background-clip:padding-box;
      background-clip:padding-box;
    }
    .modal.fade{-webkit-transition:opacity .3s linear, top .3s ease-out;-moz-transition:opacity .3s linear, top .3s ease-out;-o-transition:opacity .3s linear, top .3s ease-out;
    transition:opacity .3s linear, top .3s ease-out;top:-25%;}
    .modal.fade.in{top:50%;}
    .modal-header{padding:9px 15px;border-bottom:1px solid #eee;}.modal-header .close{margin-top:2px;}
    .modal-header h3{margin:0;line-height:30px;}
    .modal-body{overflow-y:auto;height:30em;padding:15px;}
    .modal-form{margin-bottom:0;}
    .modal-footer{padding:14px 15px 15px;margin-bottom:0;text-align:right;background-color:#f5f5f5;border-top:1px solid #ddd;-webkit-border-radius:0 0 6px 6px;-moz-border-radius:0 0 6px 6px;border-radius:0 0 6px 6px;-webkit-box-shadow:inset 0 1px 0 #ffffff;-moz-box-shadow:inset 0 1px 0 #ffffff;box-shadow:inset 0 1px 0 #ffffff;*zoom:1;}.modal-footer:before,.modal-footer:after{display:table;content:"";line-height:0;}
    .modal-footer:after{clear:both;}
    .modal-footer .btn+.btn{margin-left:1px;margin-bottom:0;}
    .modal-footer .btn-group .btn+.btn{margin-left:-1px;}



    
    
# There are a few images located here:  

/ web-app / images / bootstrap /  in the sample site



0.3 includes select method:

    <!--  method 2 - do not use with method1 in index.gsp example -->
    <!-- both method1 and method2 called breaks the functionality of this plugin -->
    <extlink:selectPref id='autolinkUpdater' noSelection="['null': 'Choose Link Method']" />
    <!--  end method 2 -->
    <br/><br/>
    <!--  final bit required by both - the bit that displays the links depending on user choice   -->
    <!--  if required you can remove all of above and hard code the choice by setting the choice=value below -->
    <!--  take a look at the source controller of plugin project or observe html produced by method 1 -->
    <extlink:returnLink link="http://www.grails.org" description="Grails Site" choice="${session.linkchoice }" />
    <extlink:returnLink link="http://www.grails.info" description="Grails INFO Site" choice="${session.linkchoice }"/>
    <extlink:returnLink link="http://www.github.com" description="GITHUB" choice="${session.linkchoice }"/>
    <extlink:modalFooter/>
    
    
    

That should be it, anything missed can be found on the sample site https://github.com/vahidhedayati/ExtLinkChoiceExample/
