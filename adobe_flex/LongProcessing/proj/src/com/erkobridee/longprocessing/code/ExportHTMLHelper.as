/*
	Classe responsável pelo processamento da grande massa de objetos
*/
package com.erkobridee.longprocessing.code
{
	import com.erkobridee.longprocessing.event.ExportHTMLEvent;
	
	import flash.display.Sprite;
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	public class ExportHTMLHelper extends Sprite
	{
		
		//----
		
		// objetos a serem processados
		private var dataArr:ArrayCollection;
		
		//----
		
		// resultado do processamento
		private var outHtml:String;
		
		//----
		
		// indica o índice dos dados que estão sendo processados
		private var indexProcess:uint;
		
		// indica o bloco de processamento de registros
		private const blocoProcess:uint = 50;
		/*
			Atenção: esse valor deve ser ajustado de acordo com a complexidade
			do objeto e quantidade de processamento executado por quadro
		*/
		
		//----
		
		
		//----------------------------------------------------------------------
		
		public function ExportHTMLHelper()
		{
			super();
			
			// registra os handlers de contrução do inicio e final do html
			this.addEventListener(ExportHTMLEvent.HTML_BEGIN, htmlBeginHandler);
			this.addEventListener(ExportHTMLEvent.HTML_END, htmlEndHandler);
		}
		
		public function buildHtml(arr:ArrayCollection):void {
			this.outHtml = "";
			this.dataArr = arr;

			this.indexProcess = 0;
			
			// cria o evento a ser disparado
			var evtBeginHtml:ExportHTMLEvent = new ExportHTMLEvent(ExportHTMLEvent.HTML_BEGIN);
			
			// chama o evento para iniciar o processamento do html
			this.dispatchEvent(evtBeginHtml);
		}
		
		//----------------------------------------------------------------------
		// handlers
		
		// trata o bloco inicial do HTML a ser gerado
		private function htmlBeginHandler(evt:ExportHTMLEvent):void {
			// chama o processamento
			this.buildBeginHtml();
			// registra o handler de processamento quando entrar em um quadro/frame do "filme" [processamento dos quadros]
			this.addEventListener(Event.ENTER_FRAME, enterFrameHandler);			
		}
		
		// processamento no respectivo quadro do filme
		private function enterFrameHandler(evt:Event):void {
			/*
				Obs.:
				- Por padrão o Flash Player bloqueia por segurança processamento contínuo
				com mais de 15 segundos, exibindo um alerta ao usuário da aplicação
				solicitando permissão para continuar o processamento.
				
				- existe um método que possibilita aumentar esse tempo até 60 segundos
				porém se você possuir muitos objetos para processar, esse tempo extendido
				não lhe será o suficiente, com isto esta tecnica irá lhe permitir 
				executar processamento de uma grande massa de dados
			
				- Obviamente essa técnica aumenta o tempo final do processamento			
			*/
			
			
			// define o próximo bloco de processamento
			var next:uint = indexProcess + blocoProcess;
			for (var k:uint = indexProcess; k < next && k < dataArr.length; k++) {
				var obj:Object = dataArr[k];	
				
				// chama o método de proccessamento do objeto
				this.buildLineTableHtml(obj);
				
			}
			// atualiza o índice de processamento
			indexProcess = k;
			
			this.dispatchProgressInfo();
			
			if(indexProcess==dataArr.length) {
				// remove o handler
				this.removeEventListener(Event.ENTER_FRAME, enterFrameHandler);
				
				// cria o evento a ser disparado
				var evtEndHtml:ExportHTMLEvent = new ExportHTMLEvent(ExportHTMLEvent.HTML_END);
				
				// chama o evento para finalizar o processamento do HTML
				this.dispatchEvent(evtEndHtml);
			}			
			
		}
		
		// trata o bloco final do HTML a ser gerado
		private function htmlEndHandler(evt:ExportHTMLEvent):void {
			// chama o processamento
			this.buildEndHtml();
			// finaliza o processamento
			this.dispacthCompletedHtml();
		}
		
		
		//----------------------------------------------------------------------
		// processamento
		
		private function buildBeginHtml():void {
			this.outHtml += "" +
				"<html>" +
				"<head><title>Long Processing Export HTML</title></head>" +
				"<body>" +
				"<center>Begin Data exported:</center><br>" +
				"<center><table>" +
				"<tr><th>Column 1</th><th>Column 2</th><th>Column 3</th></tr>";
		}
		
		private function buildLineTableHtml(o:Object):void {
			/*
				Nesse caso como a complexidade do código é nula poderiamos
				aumentar o bloco de objetos por quadro sem problemas, mas
				lembre-se que tento um código complexo aqui você deverá 
				ponderar a quantidade de objetos a ser processado por quadro
			*/
			
			this.outHtml += "" +
				"<tr><td>" +
				o.col1 +
				"</td><td>" +
				o.col2 +
				"</td><td>" +
				o.col3 +
				"</td></tr>";
		}
		
		private function buildEndHtml():void {
			this.outHtml += "" +
				"</table></center>" +
				"<br><center>:End Data exported</center>" +
				"</body>" +
				"</html>";
		}
		
		//----
		
		// dispara o evento que informa o progresso do processamento
		private function dispatchProgressInfo():void {
			var evt:ExportHTMLEvent = new ExportHTMLEvent(ExportHTMLEvent.PROGRESS);
			evt.progressValue = this.indexProcess;
			this.dispatchEvent(evt);
		}
		
		// dispara o evento com o HTML finalizado
		private function dispacthCompletedHtml():void {
			var evt:ExportHTMLEvent = new ExportHTMLEvent(ExportHTMLEvent.COMPLETED);
			evt.htmlResult = this.outHtml;
			this.dispatchEvent(evt);
		}
		
		//----------------------------------------------------------------------
		
	}
}