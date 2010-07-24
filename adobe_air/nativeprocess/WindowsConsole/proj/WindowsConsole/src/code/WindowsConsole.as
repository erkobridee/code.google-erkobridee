/*
	Erko Bridee
	Exemplo de aplicação Adobe AIR acessando um aplicativo do Windows
	[ c:/windows/system32/cmd.exe - linha de comando ]
 */
import flash.desktop.NativeProcessStartupInfo;
import flash.events.Event;
import flash.events.ProgressEvent;
import flash.filesystem.File;

import mx.managers.FocusManager;
import mx.utils.StringUtil;

//------------------------------------------------------------------------------

private var stringEncode:String = "utf-8";

private var np:NativeProcess;
private var npi:NativeProcessStartupInfo;

//------------------------------------------------------------------------------

private function init():void {
	
	outputArea.text += "osName: " + Capabilities.os + "\n\n";
	
	np = new NativeProcess();
	// registra o listener que irá recuperar o retorno do aplicativo
	np.addEventListener(ProgressEvent.STANDARD_OUTPUT_DATA, onStandardOutputData);
	
	var cmdArgs:Vector.<String> = new Vector.<String>();
	npi = new NativeProcessStartupInfo();
	// diretório inicial
	npi.workingDirectory = new File("c:/"); 
	// local onde está o cmd.exe que é programa nativo que será executado
	npi.executable = new File("c:/windows/system32/cmd.exe");
	npi.arguments = cmdArgs;
	// inicia o programa nativo
	np.start(npi);
	
	// define o foco no campo de linha de comando
	this.focusManager.setFocus(cmdField);
}

//------------------------------------------------------------------------------

// recupera o retorno e atualiza o textarea
private function onStandardOutputData(evt:ProgressEvent):void {
	var txt:String = np.standardOutput.readMultiByte(np.standardOutput.bytesAvailable, stringEncode);
	outputArea.text += txt;
}

private function onApplicationExit(event:Event):void {
	// fecha o programa nativo
	np.closeInput();
}

// envia o comando para o programa nativo
private function sendCommand():void {
	cmdField.text = StringUtil.trim(cmdField.text);
	if( 
		(cmdField.text.length > 0) &&
		this.verityCommand(cmdField.text)
	) {	
		np.standardInput.writeMultiByte(cmdField.text + "\n", stringEncode);
		cmdField.text = "";		
	}
	this.focusManager.setFocus(cmdField);
}

// implementei um comando para limpar o textarea o mesmo usado no cmd
private function verityCommand(cmd:String):Boolean {
	var executeCommandFlag:Boolean = true;
	switch( cmd.toLowerCase() ) {
		case "clear": 
		{
			outputArea.text = "";
			cmdField.text = "";
			break;
		}
		case "exit":
		{
			this.exit();
		}
			
	}
	return executeCommandFlag;
}

// mantem o scroll no final do textarea
private function updateScroll(evt:Event):void {
	outputArea.scroller.verticalScrollBar.value = outputArea.scroller.verticalScrollBar.maximum;
}

