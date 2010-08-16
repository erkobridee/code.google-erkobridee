package custom
{
	import flash.display.DisplayObject;
	import flash.display.StageDisplayState;
	import flash.events.Event;
	import flash.events.FullScreenEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Rectangle;
	import flash.system.ApplicationDomain;
	import flash.ui.Mouse;
	import flash.utils.Timer;
	
	import mx.core.FlexGlobals;
	import mx.core.IVisualElementContainer;
	import mx.events.FlexEvent;
	import mx.graphics.SolidColor;
	import mx.managers.PopUpManager;
	
	import spark.components.VideoPlayer;
	import spark.primitives.Rect;
	import spark.primitives.supportClasses.FilledElement;
	
	public class SmoothVideoDisplay extends VideoPlayer
	{
		
		public var smoothing:Boolean = false;
		
		public var fullScreen:Boolean;
		private var beforeFullScreenInfo:Object;
		private var fullScreenHideControlTimer:Timer;
		
		public var fullScreenDeblocking:Number = 0;
		public var fullScreenWidth:Number;
		public var fullScreenHeight:Number;
		public var fullScreenHideMouse:Boolean = false;
		
		public function SmoothVideoDisplay()
		{
			super();
			addEventListener( FlexEvent.CREATION_COMPLETE, onCreationComplete );
		}
		
		private function onCreationComplete(e:FlexEvent):void  
		{  
			if (videoObject.smoothing != smoothing)  
				videoObject.smoothing = smoothing;  
		}
		
		//----------------------------------------------------------------------
		/*
			código original
			http://www.flashwebdesigns.com.au/index.php/2010/07/custom-flex-4-spark-videodisplay/
		*/
		
		override protected function partAdded(partName:String, instance:Object):void
		{
			super.partAdded(partName, instance);
			
			if (instance == fullScreenButton) {
				setupFullScreenButton();
			}
		}
		
		private function setupFullScreenButton():void
		{
			fullScreenButton.addEventListener(MouseEvent.CLICK, fullScreenButtonClickListener, false, 1);
		}
		
		private function fullScreenButtonClickListener(e:MouseEvent):void
		{
			//override the default full screen event, and stop it
			e.stopImmediatePropagation();
			
			if (!fullScreen) {
				if (!systemManager.getTopLevelRoot())
					return;
				
				fullScreen = true;
				invalidateSkinState();
				
				// keep track of pauseWhenHidden b/c we will set it to false temporarily 
				// so that the video does not pause when we reparent it to the top
				// level application
				var oldPauseWhenHidden:Boolean = pauseWhenHidden;
				
				// save the current configuration of the video player
				beforeFullScreenInfo = {
					parent: 			this.parent,
					includeInLayout: 	this.includeInLayout
				};
				
				// this is for video performance reasons				
				if (videoDisplay.videoObject) {
					beforeFullScreenInfo.smoothing = videoDisplay.videoObject.smoothing;
					beforeFullScreenInfo.deblocking = videoDisplay.videoObject.deblocking;					
					
					videoDisplay.videoObject.smoothing = smoothing;
					videoDisplay.videoObject.deblocking = fullScreenDeblocking;
				}
				
				pauseWhenHidden = false;
				includeInLayout = false;
				
				// remove from old parent
				if (parent is IVisualElementContainer)
				{
					var ivec:IVisualElementContainer = IVisualElementContainer(parent);
					beforeFullScreenInfo.childIndex = ivec.getElementIndex(this);
					ivec.removeElement(this);
				}
				else
				{
					beforeFullScreenInfo.childIndex = parent.getChildIndex(this);
					parent.removeChild(this);
				}
				
				// add as a popup
				PopUpManager.addPopUp(this, FlexGlobals.topLevelApplication as DisplayObject);
				
				this.validateNow();
				
				systemManager.stage.addEventListener(FullScreenEvent.FULL_SCREEN, fullScreenEventHandler);
				
				// zoom into the video
				if (fullScreenWidth && fullScreenHeight) {
					beforeFullScreenInfo.width = width;
					beforeFullScreenInfo.height = height;
					systemManager.stage.fullScreenSourceRect = new Rectangle( 0, this.y, fullScreenWidth, fullScreenHeight);
					width = fullScreenWidth;
					height = fullScreenHeight;
				} else {					
					systemManager.stage.fullScreenSourceRect =  new Rectangle( 0, this.y, width, height);
				}
				
				//systemManager.stage.fullScreenSourceRect = new Rectangle( 0, this.y, fullScreenWidth?fullScreenWidth:width, fullScreenHeight?fullScreenHeight:height);
				// set it to fullscreen
				systemManager.stage.displayState = StageDisplayState.FULL_SCREEN;
				
				pauseWhenHidden = oldPauseWhenHidden;
				
				// TIMER CONTROLS 
				setupFullScreenTimer();
			
				
			} else {
				systemManager.stage.displayState = StageDisplayState.NORMAL;
			}
		}
		
		
		// Controls when the full screen controls fade and out
		private function setupFullScreenTimer():void
		{
			var fullScreenHideControlsDelay:Number = getStyle("fullScreenHideControlsDelay");
			
			if (fullScreenHideControlsDelay == 0)
			{
				playerControls.visible = false;
				if (fullScreenHideMouse) {
					Mouse.hide();
				}
				
				if (volumeBar)
					volumeBar.closeDropDown(true);
			}
			else if (fullScreenHideControlsDelay < Infinity)
			{
				// start timer for detecting for mouse movements/clicks to hide the controls
				fullScreenHideControlTimer = new Timer(fullScreenHideControlsDelay, 1);
				fullScreenHideControlTimer.addEventListener(TimerEvent.TIMER_COMPLETE, 
					fullScreenHideControlTimer_timerCompleteHandler, false, 0, true);
				
				// use stage or systemManager?
				systemManager.getSandboxRoot().addEventListener(MouseEvent.MOUSE_DOWN, resetFullScreenHideControlTimer);
				systemManager.getSandboxRoot().addEventListener(MouseEvent.MOUSE_MOVE, resetFullScreenHideControlTimer);
				systemManager.getSandboxRoot().addEventListener(MouseEvent.MOUSE_WHEEL, resetFullScreenHideControlTimer);
				
				// keyboard events don't happen when in fullScreen mode, but could be in fullScreen and interactive mode
				systemManager.getSandboxRoot().addEventListener(KeyboardEvent.KEY_DOWN, resetFullScreenHideControlTimer);
				
				fullScreenHideControlTimer.start();
			}
			
		}
		
		private function fullScreenEventHandler(event:FullScreenEvent):void
		{
			// going in to full screen is handled by the 
			// fullScreenButton_clickHandler
			if (event.fullScreen)
				return;
			
			// keep track of pauseWhenHidden b/c we will set it to false temporarily 
			// so that the video does not pause when we reparent it to the top
			// level application
			var exitingFullScreenPauseWhenHidden:Boolean = pauseWhenHidden;
			pauseWhenHidden = false;
			
			// set the fullScreen variable back to false and remove this event listener
			fullScreen = false;
			systemManager.stage.removeEventListener(FullScreenEvent.FULL_SCREEN, fullScreenEventHandler);
			
			// remove the event listeners to hide the controls
			systemManager.getSandboxRoot().removeEventListener(MouseEvent.MOUSE_DOWN, resetFullScreenHideControlTimer);
			systemManager.getSandboxRoot().removeEventListener(MouseEvent.MOUSE_MOVE, resetFullScreenHideControlTimer);
			systemManager.getSandboxRoot().removeEventListener(MouseEvent.MOUSE_WHEEL, resetFullScreenHideControlTimer);
			systemManager.getSandboxRoot().removeEventListener(KeyboardEvent.KEY_DOWN, resetFullScreenHideControlTimer);
			
			if (fullScreenHideControlTimer)
			{
				fullScreenHideControlTimer.stop();
				fullScreenHideControlTimer = null;
			}
			
			// make the controls visible no matter what
			playerControls.visible = true;			
			
			if (videoDisplay.videoObject && beforeFullScreenInfo.smoothing !== undefined)
			{
				videoDisplay.videoObject.smoothing = beforeFullScreenInfo.smoothing;
				videoDisplay.videoObject.deblocking = beforeFullScreenInfo.deblocking;
			}
			
			if (fullScreenWidth && fullScreenHeight) {
				width = beforeFullScreenInfo.width;
				height = beforeFullScreenInfo.height;
			}
			
			// remove from top level application:
			PopUpManager.removePopUp(this);
			
			// add back to original parent
			if (beforeFullScreenInfo.parent is IVisualElementContainer)
				beforeFullScreenInfo.parent.addElementAt(this, beforeFullScreenInfo.childIndex);
			else
				beforeFullScreenInfo.parent.addChildAt(this, beforeFullScreenInfo.childIndex);
			
			// reset pause when hidden
			pauseWhenHidden = exitingFullScreenPauseWhenHidden;
			includeInLayout = beforeFullScreenInfo.includeInLayout;
			
			beforeFullScreenInfo = null;
			
			invalidateSkinState();
			invalidateSize();
			invalidateDisplayList();			
		}		
		
		private function fullScreenHideControlTimer_timerCompleteHandler(event:TimerEvent):void
		{
			playerControls.visible = false;
			if (fullScreenHideMouse) {
				Mouse.hide();
			}
			
			if (volumeBar)
				volumeBar.closeDropDown(true);
		}
		
		private function resetFullScreenHideControlTimer(event:Event):void
		{
			playerControls.visible = true;
			Mouse.show();
			
			if (fullScreenHideControlTimer)
			{
				fullScreenHideControlTimer.reset();
				fullScreenHideControlTimer.start();
			}
			else
			{
				fullScreenHideControlTimer = new Timer(getStyle("fullScreenHideControlsDelay"), 1);
				fullScreenHideControlTimer.addEventListener(TimerEvent.TIMER_COMPLETE, 
				fullScreenHideControlTimer_timerCompleteHandler, false, 0, true);
			}
		}
		
		
		override protected function getCurrentSkinState():String
		{
			if (fullScreen)
				return super.getCurrentSkinState() + "AndFullScreen";
			else
				return super.getCurrentSkinState();	
		}
		
	}
}