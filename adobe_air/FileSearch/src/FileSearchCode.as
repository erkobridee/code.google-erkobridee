/*
	referencias

http://www.adobe.com/devnet/air/flex/quickstart/directory_search.html

http://cookbooks.adobe.com/post_Asynchronously_searching_for_files_of_a_given_exte-9383.html

este código foi adaptado do original para possibilitar parar a busca 

*/

import flash.filesystem.File;
import flash.utils.setTimeout;

import mx.collections.ArrayCollection;
import mx.controls.Alert;

[Bindable]
private var processingDir:File;

// arquivos e diretórios do diretório atual que está sendo verificado
private var currentNodes:Array = []; 
// lista de diretórios a serem verificados
private var directoryStack:Array = []; 
// sub diretórios do diretório atual
private var currentSubdirectories:Array;
// número de arquivos e diretórios verificados
private var nodeCount:uint = 0;

[Bindable]
private var resultData:ArrayCollection = new ArrayCollection(); 

// expressão regular de busca de arquivo
private var pattern:RegExp;

/**
 * Called when the application UI is rendered. The method sets the 
 * default folder path to be the user's documents directory. It also 
 * adjusts the window size and makes it visible.
 */
private function init():void 
{
	folderPath.text=File.documentsDirectory.nativePath;
	stage.nativeWindow.width = Math.min(800, Capabilities.screenResolutionX - 40);
	stage.nativeWindow.height = Capabilities.screenResolutionY - 40;
	stage.nativeWindow.visible = true;
}

/**
 * Invoked when the user clicks the Search button. This method initiates the search
 * by building a RegExp pattern based on the searchString text value, and then checking
 * to see if the specified directory exists. If the directory does not exist, an error
 * message is displayed; otherwise, the method calls the listDirectoryAsync() method of the
 * dir object (that points to the directory). The directoryListing event handler processes
 * the directory contents for this directory.
 */
private function search():void 
{
	nodeCount = 0;
	
	resultData = new ArrayCollection();
	var patternString:String = searchString.text.replace(/\./g, "\\.");
	patternString = patternString.replace(/\*/g, ".*");
	patternString = patternString.replace(/\?/g, ".");
	pattern = new RegExp(patternString, "i");
	var dir:File = new File(folderPath.text);
	if (!dir.isDirectory)
	{
		Alert.show("Invalid directory path.", "Error");
	}
	else
	{
		dir.addEventListener(FileListEvent.DIRECTORY_LISTING, dirListed);
		dir.getDirectoryListingAsync();
		
		processingDir = dir;
		
		progress.visible = true;
		btnStop.visible = true; // exibe o botão para parar a busca
	}
}

/**
 * Processes the directory contents for this directory, iterating through each node (file or
 * directory) to see if its name matches the Search pattern. Directories are added to the 
 * currentSubdirectories array, which is later added to the directoryStack. After examining
 * all of the nodes, the method then invokes the listDirectoryAsync() method of the next directory
 * in the directoryStack stack.
 */	
private function dirListed(event:FileListEvent):void 
{
	/*
		processamento da busca
	*/
	
	// recupera os arquivos e diretórios do diretório que está sendo avaliado
	currentNodes = event.files;
	progress.indeterminate = true;
	
	currentSubdirectories = []; // listagem dos subdiretórios
	nodeCount += currentNodes.length;
	progress.label = "Files and folders searched: " + nodeCount;
	var node:File;
	var fileExtension:String;
	
	// percorre os itens do diretório atual
	for (var i:int = 0; i < currentNodes.length; i++) 
	{
		node = currentNodes[i]; // item 
		if (node.isDirectory) 
		{
			// adiciona na lista de subdiretorios
			currentSubdirectories.push(currentNodes[i]);
		}
		// caso se enquadre na expressão adiciona no datagrid
		if (node.name.search(pattern) > -1) 
		{
			var newData:Object = {name:node.name,
				path:node.nativePath,
				type:node.extension}
			resultData.addItem(newData);
		}
	}

	// adiciona na lista de diretórios a serem verificados
	for (i = currentSubdirectories.length - 1; i > -1; i--) 
	{
		directoryStack.push(currentSubdirectories[i]);
	} 
	
	// remove o diretório do topo
	var dir:File = directoryStack.pop();
	if (dir == null) {
		progress.visible = false; // There are no further directories to search. The search is completed.
		btnStop.visible = false;
	} else {
		// atualiza a referencia do diretório em processamento
		processingDir = dir;		
		// relaciona ao tratamento do evento
		dir.addEventListener(FileListEvent.DIRECTORY_LISTING, dirListed);
		// inicia a verificação dos arquivos do diretório
		dir.getDirectoryListingAsync();
	}
}

/**
 * Para o processamento de busca dos arquivos
 */
private function stopSearch():void {
	// remove o tratamento do evento
	processingDir.removeEventListener(FileListEvent.DIRECTORY_LISTING, dirListed);
	// remove a referencia do objeto
	processingDir = null;
	// volta ao estado padrão
	progress.indeterminate = false;
	btnStop.visible = false;
	
	currentNodes = [];
	directoryStack = [];
	currentSubdirectories = [];	
}
