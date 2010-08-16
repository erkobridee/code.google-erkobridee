/*
	baseado no código original
	http://www.derekentringer.com/blog/flex-file-browser/
*/

import flash.filesystem.File;
import flash.html.HTMLLoader;
import flash.media.Video;
import flash.net.URLRequest;

import mx.collections.*;
import mx.controls.Alert;
import mx.core.UIComponent;
import mx.events.FlexEvent;
import mx.events.MenuEvent;
import mx.utils.StringUtil;

[Bindable]
public var menuBarCollection:XMLListCollection;

private var menubarXML:XMLList =
	<>
		<menuitem label="File" data="top">
			<menuitem label="Browse" data="browse_files"/>
		</menuitem>
	</>;

private function menuHandler(event:MenuEvent):void  {
	if (event.item.@data != "top") {
		if(event.item.@data == "browse_files"){
			browseDir(event);
		}
	}        
}

private function init():void{
	fileSystemTree.directory = File.desktopDirectory;
	
	openDirField.text = fileSystemTree.directory.nativePath;
	
	menuBarCollection = new XMLListCollection(menubarXML);
}

private function browseDir(e:Event):void{
	var dir:File = new File();
	dir.browseForDirectory("Select directory");
	dir.addEventListener(Event.SELECT, onDirSelect);
}

private function onDirSelect(event:Event):void{
	fileSystemTree.directory = event.currentTarget as File;
	
	openDirField.text = fileSystemTree.directory.nativePath; 
}


private function btnOpenDir_clickHandler(event:MouseEvent):void
{
	this.openDir();
}

private function openDirField_enterHandler(event:FlexEvent):void
{
	this.openDir();
}

private function openDir():void {
	var dir:String = StringUtil.trim(openDirField.text);
	if(dir.length > 0) {
		var file:File = new File(dir);
		if(file.exists && file.isDirectory) {
			fileSystemTree.directory = file;
		} else {
			Alert.show("Invalid dir", "OpenDir Field");
		}
	} else {
		Alert.show("Invalid dir path", "OpenDir Field");
	}
}

private function onChange(e:Event):void{
	var file:File = e.currentTarget.selectedItem as File;
	
	if(currentState == "mp3State") {
		mp3_content.pause();
	}
	
	if(!file.isDirectory){
		var viewer:Object;
		switch(file.extension.toLowerCase()){
			case "gif":
			case "png":
			case "jpg":
			case "pdf":
			case "txt":
			case "htm":
			case "html":
				currentState = "htmlState";
				viewer = html_content;
				viewer.location = file.url;				
				break
			case "mov":
			case "mp4":
			case "flv":
				currentState = "videoState";
				viewer = videoPlayer;
				videoPlayer.source = file.url;
				break;
			case "mp3":
				currentState = "mp3State";
				viewer = mp3_content;
				viewer.source = file.url;			
				break;
			default:				
				// abre com o aplicativo nativo do computador
				file.openWithDefaultApplication();
				
				return;
		}
	}
}