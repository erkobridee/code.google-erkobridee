import com.erkobridee.longprocessing.code.ExportHTMLHelper;
import com.erkobridee.longprocessing.event.ExportHTMLEvent;

import flash.net.FileReference;

import mx.collections.ArrayCollection;

private const STATE_DEFAULT:String = "STATE_DEFAULT";
private const STATE_PROCESSING:String = "STATE_PROCESSING";
private const STATE_SAVE:String = "STATE_SAVE";

//------------------------------------------------------------------------------

[Bindable]
private var dataArray:ArrayCollection;

private var helper:ExportHTMLHelper;

private var outHtml:String;

//------------------------------------------------------------------------------

private function init():void {
	
	dataArray = new ArrayCollection();
	
	for( var i:Number = 0; i < 10000; i++ ) {
		var o:Object = {};
		o.col1 = "c1 value " + (i+1);
		o.col2 = "c2 value " + (i+1);
		o.col3 = "c3 value " + (i+1);
		dataArray.addItem(o);
	}
	
}

private function exportHTML():void {	
	this.currentState = STATE_PROCESSING;
	
	this.outHtml = "";
	
	helper = new ExportHTMLHelper();
	// cadastra os handlers
	helper.addEventListener(ExportHTMLEvent.PROGRESS, htmlExportProgressHandler);
	helper.addEventListener(ExportHTMLEvent.COMPLETED, htmlExportCompletedHandler);
	
	// inicia o processamento
	helper.buildHtml(this.dataArray);
}

private function saveHTML():void {
	this.currentState = STATE_DEFAULT;
	
	helper = null;
	
	var fr:FileReference = new FileReference();
	fr.save( this.outHtml, "data.html" );
	
}

//------------------------------------------------------------------------------

private function htmlExportProgressHandler(evt:ExportHTMLEvent):void {
	this.progress.setProgress(evt.progressValue, this.dataArray.length);	
	this.statusInfo.text = evt.progressValue + " / " + this.dataArray.length;
}

private function htmlExportCompletedHandler(evt:ExportHTMLEvent):void {
	this.currentState = STATE_SAVE;
	
	// remove os handlers do helper
	this.removeEventListener(ExportHTMLEvent.PROGRESS, htmlExportProgressHandler);
	this.removeEventListener(ExportHTMLEvent.COMPLETED, htmlExportCompletedHandler);
	
	this.outHtml = evt.htmlResult;
	
}
