

	Trabalho de Processamento de Imagens Digitais

Acad�mico: Erko Bridee de Almeida Cabrera
4� ano - curso bacharel em Ci�ncia da Computa��o - Cesufoz
e-mail: erko.bridee@gmail.com

Projeto desenvolvido utilizando a linguagem de programa��o Java
sdk: j2sdk 1.4.2
IDE: Eclipse 3.0.2

----------------------------------------------------------------------
	2� Bimestre
----------------------------------------------------------------------

- Escala de Cinza

Converte uma imagem RGB para tons de cinza, onde para cada pixel
� recuperado o valor dos 3 canais de cores e realizado o um calculo
de equivalencia das 3 cores para o cinza( Padr�o NTSC ):

cinza = 0.2989 * Vermelho + 0.5870 * Verde + 0.1140 * Azul 

ap�s calculado o cinza esse valor � definido como valor para os 3 canais
de cor:

Vermelho = Cinza
Verde = Cinza
Azul = Cinza

com isso definindo a nova cor do pixel para o seu equivalente da escala de cinza


- Limiariza��o

Calculo de Limiariza��o � aquele onde ap�s eleito/informado
um valor como limiar percorre a imagem e verifica o valor 
do pixel onde caso o valor seja maior que o limiar seta o
seu valor para 255 e caso seja menor, seta o seu valor para 0.


- Filtro M�dia

� um filtro de suaviza��o da imagem utilizado para borramento e redu��o
de ru�do na imagem. O borramento � utilizado em pr�-processamento, tais
como remo��o de pequenos detalhes de uma imagem antes da extra��o de
objettos( grandes ), e conex�o de pequenas descontinuidades em linhas e
curvas.

� um filtro do tipo passa-baixa( filtragem de suaviza��o )

O filtro m�dia � um dos tipos mais simples de filtros passa-baixas 
e o seu efeito � o de substituir o n�vel de cinza de um pixel pela 
m�dia aritm�tica do pixel e de seus vizinhos.

Vantagem: preserva as bordas na imagem, suaviza��o da imagem (efeito de desfocagem), 
bom para elimina��o de ru�dos, se ocaso for imprimir a imagem.

Desvantagem: perda de informa��o.

Funcionamento:
Percorre a imagem e em cada posi��o do pixel que est� sendo 
verificado, recupera os seus respectivos vizinhos de acordo com o 
tamanho da mascara aplicada realiza a soma de todos os valores dos pixels
e divide pelo tamanho da dimens�o da matriz de mascara aplicada, � uma
opera��o de m�dia aritm�tica do pixel com os seus respectivos vizinhos.

Exemplo de mascara 3x3
+---+---+---+
| 1 | 1 | 1 |
+---+---+---+
| 1 | 1 | 1 | * 1/( 3x3 )
+---+---+---+
| 1 | 1 | 1 |
+---+---+---+

----------------------------------------------------------------------
	3� Bimestre
----------------------------------------------------------------------

 - Filtro Mediana

� um filtro de suaviza��o da imagem utilizado para borramento e redu��o
de ru�do na imagem. O borramento � utilizado em pr�-processamento, tais
como remo��o de pequenos detalhes de uma imagem antes da extra��o de
objettos( grandes ), e conex�o de pequenas descontinuidades em linhas e
curvas.

� um filtro do tipo passa-baixa( filtragem de suaviza��o )

Neste tipo de filtro passa-baixas, o pixel central da m�scara � substitu�do 
pelo valor mediano dos seus vizinhos.

Vantagem: preserva as bordas na imagem; homogeiniza a imagem.

Funcionamento:
Pega os valores da imagem em uma regi�o 3x3
ordenando em ordem crescente os valores

ap�s ordenar recupera a quinta posi��o 
pois esta corresponde ao valor m�dio da regi�o 3x3

Exemplo:
+----+----+----+
| 10 |  8 | 15 |
+----+----+----+
| 16 | 20 | 12 | ---> 10,8,15,16,20,12,19,7,5
+----+----+----+
| 19 |  7 |  5 |
+----+----+----+

ordenando: 5,7,8,10,{12},15,16,19,20
                 valor m�dio
                 ( mediana )


 - Filtro Passa Alta 

� um filtro de agu�amento onde o objetivo � enfatizar detalhes finos 
em uma imagem ou real�ar detalhes que tenham sido borrados, em consequ�ncia
de erros ou como efeito natual de um m�todo particular de aquisi��o de imagens.
O uso de agu�amento de imagens s�o variados, incluindo aplica��es desde
impress�o eletr�nica e imagens m�dicas at� inspe��o industrial e detec��o
autom�tica de alvos em armas inteligentes. 

Utiliza��o: realce e detec��o de bordas.

Funcionamento:
Percorre a imagem aplicando um mascara onde esta ir� real�ar/agu�ar o pixel
que est� sendo analisado com rela��o aos seus vizinhos.

Mascara do filtro
+---+---+---+
| -1| -1| -1|
+---+---+---+
| -1| 8 | -1|
+---+---+---+
| -1| -1| -1|
+---+---+---+


 - Filtro Alto Refor�o ( Righ Boost )

� um filtro de agu�amento onde o objetivo � enfatizar detalhes finos 
em uma imagem ou real�ar detalhes que tenham sido borrados, em consequ�ncia
de erros ou como efeito natual de um m�todo particular de aquisi��o de imagens.
O uso de agu�amento de imagens s�o variados, incluindo aplica��es desde
impress�o eletr�nica e imagens m�dicas at� inspe��o industrial e detec��o
autom�tica de alvos em armas inteligentes. 

Utiliza��o: realce e detec��o de bordas, com determina��o do agu�amento do pixel.

Funcionamento:
Percorre a imagem calculando para cada pixel o seguinte calculo,
onde o pixel atual pode ter o seu grau de agu�amento com rela��o aos seus
vizinhos, onde esse agu�amento � definido:

Mascara do filtro
+---+---+---+
| -1| -1| -1|
+---+---+---+
| -1| W | -1| --> W = 9*A - 1, sendo A o fator de amplia��o( A >= 1 ) 
+---+---+---+
| -1| -1| -1|
+---+---+---+

----------------------------------------------------------------------
	4� Bimestre
----------------------------------------------------------------------

- Filtro Roberts

� um tipo de filtro passa alta e filtro direcional.

O filtro Roberts � um filtro n�o-linear parecido com o filtro Sobel 
e representa uma aproxima��o � fun��o de Roberts. O tamanho da m�scara (2 x 2) n�o pode ser mudado.

Vantagem: real�ar e isolar bordas em dire��es predeterminadas.

Desvantagem: produz muitas vezes bordas artificiais, que podem confundir o int�rprete.

Funcionamento:
Percorre a imagem calculando a hipotenusa utilizando as seguintes mascaras abaixo.
Realiza o seguinte calculo:

f = | Gx� + Gy� |�


Mascaras:

+-------+--------+
| x,y   | x+1,y  |
+-------+--------+
| x,y-1 | x+1,y+1|
+-------+--------+

Gx         Gy
+---+---+  +---+---+
|  1|  0|  |  0|  1|
+---+---+  +---+---+
|  0| -1|  | -1|  0|
+---+---+  +---+---+


- Filtro Sobel

� um tipo de filtro passa alta e filtro direcional.

O filtro Sobel � um filtro n�o-linear para real�ar bordas e 
representa uma aproxima��o � fun��o de Sobel. 
O tamanho da m�scara (3 x 3) e n�o pode ser mudado.

Vantagem: detec��o de bordas.

Desvantagem: produz, muitas vezes, bordas artificiais, que podem confundir o int�rprete.

Funcionamento:
Percorre a imagem calculando o gradiente do pixel no com rela��o a X e ao Y
realizando o seguinte calculo:
f = | Gx | + | Gy |

Mascaras:

Gx
+---+---+---+
| -1| -2| -1|
+---+---+---+
| 0 | 0 | 0 |
+---+---+---+
| 1 | 2 | 1 |
+---+---+---+

Gy
+---+---+---+
| -1| 0 | 1 |
+---+---+---+
| -2| 0 | 2 |
+---+---+---+
| -1| 0 | 1 |
+---+---+---+


- Filtro Prewitt

� similar ao filtro de Sobel onde apenas as mascaras de Gx e Gy do
calculo s�o diferente, sendo o processo de calculo igual ao 
realizado pelo filtro de Sobel.


Mascaras:

Gx
+---+---+---+
| -1| -1| -1|
+---+---+---+
| 0 | 0 | 0 |
+---+---+---+
| 1 | 1 | 1 |
+---+---+---+

Gy
+---+---+---+
| -1| 0 | 1 |
+---+---+---+
| -1| 0 | 1 |
+---+---+---+
| -1| 0 | 1 |
+---+---+---+

- Filtro Laplace

Este filtro � �til na detec��o de bordas. 
Geralmente, a soma dos pesos da m�scara � igual a zero 
(Cr�sta, 1993, p. 85). Ele usa uma m�scara com um alto valor central, 
cercado de valores negativos nas dire��es N-S e E-W e o valor zero para os pesos da m�scara.

Vantagem: detec��o de bordas.

Desvantagem: n�o considera a dire��o das bordas.

Mascara do filtro:
+---+---+---+
| 0 | -1| 0 |
+---+---+---+
| -1| 4 | -1|
+---+---+---+
| 0 | -1| 0 |
+---+---+---+


----------------------------------------------------------------------
	Extras
----------------------------------------------------------------------

- Quantiza��o

A quantiza��o utiliza de um valor informado de quantizador, o
que representa a quantidade de cores que a imagem ser� representada,
por exemplo: quando informado 16, ele ir� realizar a verifica��o
do valor do pixel e enquadrar em um dos 16 intervalor possiveis
de cor entre 0 a 255, com isso redefinindo a imagem para 16
tons de cor( quantizando a imagem ).


- Filtro Personalizado

No filtro personalizado � informado uma matriz 3x3
a qual � aplicada sobre uma imagem RGB ou em escala
de cinza.


**********************************************************************
----------------------------------------------------------------------
	MASCARAS INTERESSANTES
----------------------------------------------------------------------

TESTES
{{1,-2,1},{-2,1,-2},{1,-2,5}};
{{0,-1,0},{-1,5,-1},{0,-1,0}};
{{1,-2,1},{-2,5,-2},{1,-2,1}};
{{1,-2,1},{-2,5,-2},{1,-2,1}};

----

Os filtros de realce de bordas real�am a cena, 
segundo dire��es preferenciais de interesse, 
definidas pelas m�scaras. Abaixo est�o algumas utilizadas 
para o real�amento de bordas em v�rios sentidos. 
O nome dado �s m�scaras indica a dire��o ortogonal preferencial 
em que ser� real�ado o limite de borda. 
Assim, a m�scara norte real�a limites horizontais.

NORTE
{{1,1,1},{1,-2,1},{-1,-1,-1}};

NORTEDESTE
{{1,1,1},{-1,-2,1},{-1,-1,1}};

LESTE
{{-1,1,1},{-1,-2,1},{-1,1,1}};

SUDESTE
{{-1,-1,1},{-1,-2,1},{1,1,1}};

SUL
{{-1,-1,-1},{1,-2,1},{1,1,1}};

SUDOESTE
{{1,-1,-1},{1,-2,-1},{1,1,1}};

OESTE
{{1,1,-1},{1,-2,-1},{1,1,-1}};

NOROESTE
{{1,1,1},{1,-2,-1},{1,-1,-1}};

----

Realce n�o-direcional de bordas: � utilizado para real�ar bordas, 
independentemente da dire��o. As tr�s m�scaras mais comuns diferem 
quanto � intensidade de altos valores de n�veis de cinza presentes 
na imagem resultante. A m�scara alta deixa passar menos os baixos 
n�veis de cinza, isto �, a imagem fica mais clara. 
A m�scara baixa produz uma imagem mais escura que a anterior. 
A m�scara m�dia apresenta resultados intermedi�rios.

ALTA
{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};

MEDIA
{{0,-1,0},{-1,4,-1},{0,-1,0}};

BAIXA
{{1,-2,1},{-2,3,-2},{1,-2,1}};

----

Realce de imagens: Utiliza m�scaras apropriadas ao realce de caracter�sticas 
de imagens obtidas por um sensor espec�fico. Para imagens TM/Landsat o realce 
compensa distor��es radiom�tricas do sensor. 
O pixel que ter� seu valor de n�vel de cinza substitu�do pela aplica��o da m�scara, 
corresponde � posi��o sombreada.

{{3,-7,-7,3},{-7,-7,13,-7},{-7,13,13,-7},{3,-7,-7,3}};

----

