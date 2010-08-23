

	Trabalho de Processamento de Imagens Digitais

Acadêmico: Erko Bridee de Almeida Cabrera
4º ano - curso bacharel em Ciência da Computação - Cesufoz
e-mail: erko.bridee@gmail.com

Projeto desenvolvido utilizando a linguagem de programação Java
sdk: j2sdk 1.4.2
IDE: Eclipse 3.0.2

----------------------------------------------------------------------
	2º Bimestre
----------------------------------------------------------------------

- Escala de Cinza

Converte uma imagem RGB para tons de cinza, onde para cada pixel
é recuperado o valor dos 3 canais de cores e realizado o um calculo
de equivalencia das 3 cores para o cinza( Padrão NTSC ):

cinza = 0.2989 * Vermelho + 0.5870 * Verde + 0.1140 * Azul 

após calculado o cinza esse valor é definido como valor para os 3 canais
de cor:

Vermelho = Cinza
Verde = Cinza
Azul = Cinza

com isso definindo a nova cor do pixel para o seu equivalente da escala de cinza


- Limiarização

Calculo de Limiarização é aquele onde após eleito/informado
um valor como limiar percorre a imagem e verifica o valor 
do pixel onde caso o valor seja maior que o limiar seta o
seu valor para 255 e caso seja menor, seta o seu valor para 0.


- Filtro Média

É um filtro de suavização da imagem utilizado para borramento e redução
de ruído na imagem. O borramento é utilizado em pré-processamento, tais
como remoção de pequenos detalhes de uma imagem antes da extração de
objettos( grandes ), e conexão de pequenas descontinuidades em linhas e
curvas.

É um filtro do tipo passa-baixa( filtragem de suavização )

O filtro média é um dos tipos mais simples de filtros passa-baixas 
e o seu efeito é o de substituir o nível de cinza de um pixel pela 
média aritmética do pixel e de seus vizinhos.

Vantagem: preserva as bordas na imagem, suavização da imagem (efeito de desfocagem), 
bom para eliminação de ruídos, se ocaso for imprimir a imagem.

Desvantagem: perda de informação.

Funcionamento:
Percorre a imagem e em cada posição do pixel que está sendo 
verificado, recupera os seus respectivos vizinhos de acordo com o 
tamanho da mascara aplicada realiza a soma de todos os valores dos pixels
e divide pelo tamanho da dimensão da matriz de mascara aplicada, é uma
operação de média aritmética do pixel com os seus respectivos vizinhos.

Exemplo de mascara 3x3
+---+---+---+
| 1 | 1 | 1 |
+---+---+---+
| 1 | 1 | 1 | * 1/( 3x3 )
+---+---+---+
| 1 | 1 | 1 |
+---+---+---+

----------------------------------------------------------------------
	3º Bimestre
----------------------------------------------------------------------

 - Filtro Mediana

É um filtro de suavização da imagem utilizado para borramento e redução
de ruído na imagem. O borramento é utilizado em pré-processamento, tais
como remoção de pequenos detalhes de uma imagem antes da extração de
objettos( grandes ), e conexão de pequenas descontinuidades em linhas e
curvas.

É um filtro do tipo passa-baixa( filtragem de suavização )

Neste tipo de filtro passa-baixas, o pixel central da máscara é substituído 
pelo valor mediano dos seus vizinhos.

Vantagem: preserva as bordas na imagem; homogeiniza a imagem.

Funcionamento:
Pega os valores da imagem em uma região 3x3
ordenando em ordem crescente os valores

após ordenar recupera a quinta posição 
pois esta corresponde ao valor médio da região 3x3

Exemplo:
+----+----+----+
| 10 |  8 | 15 |
+----+----+----+
| 16 | 20 | 12 | ---> 10,8,15,16,20,12,19,7,5
+----+----+----+
| 19 |  7 |  5 |
+----+----+----+

ordenando: 5,7,8,10,{12},15,16,19,20
                 valor médio
                 ( mediana )


 - Filtro Passa Alta 

É um filtro de aguçamento onde o objetivo é enfatizar detalhes finos 
em uma imagem ou realçar detalhes que tenham sido borrados, em consequência
de erros ou como efeito natual de um método particular de aquisição de imagens.
O uso de aguçamento de imagens são variados, incluindo aplicações desde
impressão eletrônica e imagens médicas até inspeção industrial e detecção
automática de alvos em armas inteligentes. 

Utilização: realce e detecção de bordas.

Funcionamento:
Percorre a imagem aplicando um mascara onde esta irá realçar/aguçar o pixel
que está sendo analisado com relação aos seus vizinhos.

Mascara do filtro
+---+---+---+
| -1| -1| -1|
+---+---+---+
| -1| 8 | -1|
+---+---+---+
| -1| -1| -1|
+---+---+---+


 - Filtro Alto Reforço ( Righ Boost )

É um filtro de aguçamento onde o objetivo é enfatizar detalhes finos 
em uma imagem ou realçar detalhes que tenham sido borrados, em consequência
de erros ou como efeito natual de um método particular de aquisição de imagens.
O uso de aguçamento de imagens são variados, incluindo aplicações desde
impressão eletrônica e imagens médicas até inspeção industrial e detecção
automática de alvos em armas inteligentes. 

Utilização: realce e detecção de bordas, com determinação do aguçamento do pixel.

Funcionamento:
Percorre a imagem calculando para cada pixel o seguinte calculo,
onde o pixel atual pode ter o seu grau de aguçamento com relação aos seus
vizinhos, onde esse aguçamento é definido:

Mascara do filtro
+---+---+---+
| -1| -1| -1|
+---+---+---+
| -1| W | -1| --> W = 9*A - 1, sendo A o fator de ampliação( A >= 1 ) 
+---+---+---+
| -1| -1| -1|
+---+---+---+

----------------------------------------------------------------------
	4º Bimestre
----------------------------------------------------------------------

- Filtro Roberts

É um tipo de filtro passa alta e filtro direcional.

O filtro Roberts é um filtro não-linear parecido com o filtro Sobel 
e representa uma aproximação à função de Roberts. O tamanho da máscara (2 x 2) não pode ser mudado.

Vantagem: realçar e isolar bordas em direções predeterminadas.

Desvantagem: produz muitas vezes bordas artificiais, que podem confundir o intérprete.

Funcionamento:
Percorre a imagem calculando a hipotenusa utilizando as seguintes mascaras abaixo.
Realiza o seguinte calculo:

f = | Gx² + Gy² |½


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

É um tipo de filtro passa alta e filtro direcional.

O filtro Sobel é um filtro não-linear para realçar bordas e 
representa uma aproximação à função de Sobel. 
O tamanho da máscara (3 x 3) e não pode ser mudado.

Vantagem: detecção de bordas.

Desvantagem: produz, muitas vezes, bordas artificiais, que podem confundir o intérprete.

Funcionamento:
Percorre a imagem calculando o gradiente do pixel no com relação a X e ao Y
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

É similar ao filtro de Sobel onde apenas as mascaras de Gx e Gy do
calculo são diferente, sendo o processo de calculo igual ao 
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

Este filtro é útil na detecção de bordas. 
Geralmente, a soma dos pesos da máscara é igual a zero 
(Crósta, 1993, p. 85). Ele usa uma máscara com um alto valor central, 
cercado de valores negativos nas direções N-S e E-W e o valor zero para os pesos da máscara.

Vantagem: detecção de bordas.

Desvantagem: não considera a direção das bordas.

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

- Quantização

A quantização utiliza de um valor informado de quantizador, o
que representa a quantidade de cores que a imagem será representada,
por exemplo: quando informado 16, ele irá realizar a verificação
do valor do pixel e enquadrar em um dos 16 intervalor possiveis
de cor entre 0 a 255, com isso redefinindo a imagem para 16
tons de cor( quantizando a imagem ).


- Filtro Personalizado

No filtro personalizado é informado uma matriz 3x3
a qual é aplicada sobre uma imagem RGB ou em escala
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

Os filtros de realce de bordas realçam a cena, 
segundo direções preferenciais de interesse, 
definidas pelas máscaras. Abaixo estão algumas utilizadas 
para o realçamento de bordas em vários sentidos. 
O nome dado às máscaras indica a direção ortogonal preferencial 
em que será realçado o limite de borda. 
Assim, a máscara norte realça limites horizontais.

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

Realce não-direcional de bordas: é utilizado para realçar bordas, 
independentemente da direção. As três máscaras mais comuns diferem 
quanto à intensidade de altos valores de níveis de cinza presentes 
na imagem resultante. A máscara alta deixa passar menos os baixos 
níveis de cinza, isto é, a imagem fica mais clara. 
A máscara baixa produz uma imagem mais escura que a anterior. 
A máscara média apresenta resultados intermediários.

ALTA
{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};

MEDIA
{{0,-1,0},{-1,4,-1},{0,-1,0}};

BAIXA
{{1,-2,1},{-2,3,-2},{1,-2,1}};

----

Realce de imagens: Utiliza máscaras apropriadas ao realce de características 
de imagens obtidas por um sensor específico. Para imagens TM/Landsat o realce 
compensa distorções radiométricas do sensor. 
O pixel que terá seu valor de nível de cinza substituído pela aplicação da máscara, 
corresponde à posição sombreada.

{{3,-7,-7,3},{-7,-7,13,-7},{-7,13,13,-7},{3,-7,-7,3}};

----

