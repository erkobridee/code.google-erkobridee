package com.erkobridee.longprocessing.event
{
	import flash.events.Event;
	
	public class ExportHTMLEvent extends Event
	{
		
		//----------------------------------------------------------------------
		
		public static const HTML_BEGIN:String = "ExportHTMLEvent_htmlbegin";
		public static const HTML_END:String = "ExportHTMLEvent_htmlend";
		
		public static const COMPLETED:String = "ExportHTMLEvent_completed";
		
		public static const PROGRESS:String = "ExportHTMLEvent_progress";
		
		//----------------------------------------------------------------------
		
		// quantidade de objetos processados
		public var progressValue:Number;
		
		// html final gerado do processamento
		public var htmlResult:String;
		
		//----------------------------------------------------------------------
		
		public function ExportHTMLEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}