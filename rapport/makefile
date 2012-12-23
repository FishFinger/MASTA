tex_cmd=pdflatex
file_name=main

all: main.pdf

%.pdf: %.tex *.tex
	${tex_cmd} $^

index:
	makeindex ${file_name}.glo -s ${file_name}.ist -t ${file_name}.glg -o ${file_name}.gls

clean:
	@rm -f *.aux
	@rm -f *.glg
	@rm -f *.glo
	@rm -f *.gls
	@rm -f *.ist
	@rm -f *.log
	@rm -f *.maf
	@rm -f *.mtc*
	@rm -f *.out
	@rm -f *.toc

mrproper:
	make clean
	@rm -f *~
	@rm -f *.pdf