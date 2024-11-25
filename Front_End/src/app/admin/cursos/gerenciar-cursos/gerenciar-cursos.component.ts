import { Component, OnInit } from '@angular/core';
import { CcursoService } from '../../../serv/admin/ccurso.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-gerenciar-cursos',
  templateUrl: './gerenciar-cursos.component.html',
  styleUrls: ['./gerenciar-cursos.component.css']
})
export class GerenciarCursosComponent implements OnInit {
  cursos: any[] = [];
  cursosFiltrados: any[] = []; // Cursos filtrados após a pesquisa
  cursosPaginados: any[] = []; // Cursos exibidos na página atual
  pesquisaNome: string = ''; // Valor do campo de pesquisa
  loading: boolean = false;
  paginaAtual: number = 1;
  totalPaginas: number = 1;
  itensPorPagina: number = 8;
  paginas: number[] = [];

  constructor(private ccursoService: CcursoService, private titleService: Title) { }

  ngOnInit(): void {
    this.carregarCursos();
    this.titleService.setTitle('Gerenciamento de Cursos');
  }

  carregarCursos() {
    this.ccursoService.getCursos().subscribe(
      data => {
        this.cursos = data;
        this.cursosFiltrados = this.cursos; // Inicialmente, todos os cursos estão na lista filtrada
        this.calcularPaginas();
        this.atualizarCursosPaginados();
      },
      error => {
        console.error('Erro ao carregar cursos: ', error);
      }
    );
  }

  calcularPaginas() {
    this.totalPaginas = Math.ceil(this.cursosFiltrados.length / this.itensPorPagina);
    this.paginas = Array.from({ length: this.totalPaginas }, (_, i) => i + 1);
  }

  atualizarCursosPaginados() {
    const inicio = (this.paginaAtual - 1) * this.itensPorPagina;
    const fim = inicio + this.itensPorPagina;
    this.cursosPaginados = this.cursosFiltrados.slice(inicio, fim);
  }

  irParaPagina(pagina: number) {
    if (pagina >= 1 && pagina <= this.totalPaginas) {
      this.paginaAtual = pagina;
      this.atualizarCursosPaginados();
    }
  }

  // Função de filtragem por nome
  filtrarCursos() {
    this.cursosFiltrados = this.cursos.filter(curso =>
      curso.courseName.toLowerCase().includes(this.pesquisaNome.toLowerCase())
    );
    this.paginaAtual = 1; // Resetar para a primeira página ao filtrar
    this.calcularPaginas();
    this.atualizarCursosPaginados();
  }

  excluirCurso(id: number) {
    if (confirm('Tem certeza que deseja excluir este curso?')) {
      this.loading = true;
      this.ccursoService.deleteCurso(id).subscribe(
        () => {
          this.cursos = this.cursos.filter(curso => curso.courseId !== id);
          this.filtrarCursos(); // Reaplica o filtro após a exclusão
          this.loading = false;
          alert('Curso excluído com sucesso!');
        },
        error => {
          console.error('Erro ao excluir curso: ', error);
          this.loading = false;
          alert('Ocorreu um erro ao excluir o curso.');
        }
      );
    }
  }
}
