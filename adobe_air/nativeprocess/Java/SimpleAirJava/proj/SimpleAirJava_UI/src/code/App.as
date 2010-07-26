
import flash.desktop.NativeProcess;
import flash.desktop.NativeProcessStartupInfo;
import flash.filesystem.File;

import mx.utils.StringUtil;

private var stringEncode:String = "utf-8";

private var np:NativeProcess;
private var npi:NativeProcessStartupInfo;

//------------------------------------------------------------------------------

private function init():void {
	this.focusManager.setFocus(msgField);	
	writeLogArea = "osName: " + Capabilities.os + "\n\n";
	
	javawField.text = findJavaw.nativePath;
	
	//this.javaNativeProcess();
}

private function startJava():void {
	javawField.text = StringUtil.trim(javawField.text);
	if(javawField.text.length > 0) {
		var javaw:File = new File(javawField.text);
		if( javaw.exists ) {
			writeLogArea = "javaw encontrado...\n\n";
			writeLogArea = "caminho: " + javaw.nativePath + "\n\n";
			writeLogArea = "Retorno do java: \n\n";
			
			javawField.editable = false;
			btnStart.enabled = false;
			
			this.javaNativeProcess(javaw);
		} else {
			writeLogArea = "javaw não encontrado...\n\n";
			this.focusManager.setFocus(javawField);
		}
	} else {
		this.focusManager.setFocus(javawField);
	}
	
}

private function javaNativeProcess(javaw:File):void {
	
	np = new NativeProcess();
	// registra o listener que irá recuperar o retorno do aplicativo
	np.addEventListener(ProgressEvent.STANDARD_OUTPUT_DATA, onStandardOutputData);
	
	var jarFile:File = File.applicationDirectory.resolvePath("jars/RunApp.jar");
	
	if( jarFile.exists ) {
		writeLogArea = "jar encontrado...\n\n";
		writeLogArea = "caminho: " + jarFile.nativePath + "\n\n";
	} else {
		writeLogArea = "jar não encontrado...\n\n";
	}
	
	var args:Vector.<String> = new Vector.<String>;
	
	// 2 formas de inicializar a aplicação java	
	args.push("-cp"); // define o classpath
	args.push(jarFile.nativePath); // caminho do jar da app java
	args.push("com.erkobridee.simpleairjava.RunApp"); // classe de execução
	
	/* caso o jar tenha definido uma classe de execução
	args.push("-jar");
	args.push(jarFile.nativePath);
	*/
	
	// parametros de inicialização da aplicação
	args.push( "init param1" );
	args.push( "init param2" );

	writeLogArea = "javaw encontrado...\n\n";
	writeLogArea = "caminho: " + javaw.nativePath + "\n\n";
	writeLogArea = "Retorno do java: \n\n";
	
	npi = new NativeProcessStartupInfo();
	npi.executable = javaw;
	npi.arguments = args;
	
	np.start(npi);

}

//------------------------------------------------------------------------------

private function onStandardOutputData(evt:ProgressEvent):void {
	var txt:String = np.standardOutput.readMultiByte(np.standardOutput.bytesAvailable, stringEncode);
	writeLogArea = txt;
}


public function onApplicationExit(event:Event):void {
	// fecha o programa nativo
	np.closeInput();
	/*
		essa foi linha foi necessária par finalizar o processo java, 
		sem ela a aplicação java continua executando 
		após fechar a aplicação Adobe AIR
	*/
	np.exit(true);
}

private function sendMessage():void {
	msgField.text = StringUtil.trim(msgField.text);
	if( msgField.text.length > 0) {	
		np.standardInput.writeMultiByte(msgField.text + "\n", stringEncode);
		msgField.text = "";		
	}
	this.focusManager.setFocus(msgField);

}

private function set writeLogArea(msg:String):void {
	logArea.text = msg + logArea.text;
}


//------------------------------------------------------------------------------

private function get findJavaw():File
{
	var result:File;
	var osName:String = Capabilities.os.toLowerCase();
	
	if (osName.indexOf("win") > -1) {
		/*
		A partir do Java 1.6 no windows já se encontra este arquivo
		no espectivo local indicado
		*/
		result = new File("c:/windows/system32/javaw.exe");		
	} 
	else if (osName.indexOf("mac") > -1)
	{
		result = getJavaOnUnix("/System/Library/Frameworks/JavaVM.framework/Versions/Current/Commands/java");
	} 
	else if (osName.indexOf("linux") > -1) 
	{
		result = getJavaOnUnix("/etc/alternatives/java");
	}
	
	return result;
}		

private function getJavaOnUnix(alternativePath:String):File
{
	var java:File = new File("/usr/bin/java");
	if (!java.exists)
		java = new File(alternativePath);
	return java;
}	


