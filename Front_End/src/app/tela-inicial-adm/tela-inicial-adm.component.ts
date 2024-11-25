import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tela-inicial-adm',
  templateUrl: './tela-inicial-adm.component.html',
  styleUrls: ['./tela-inicial-adm.component.css']
})
export class TelaInicialAdmComponent implements OnInit {
  saudacao: string = '';

  constructor(private titleService: Title) {}

  ngOnInit(): void {
    this.titleService.setTitle('Página Inicial - Administração');  // Define o título da aba
    this.definirSaudacao();
  }

  definirSaudacao(): void {
    const hora = new Date().getHours();
    if (hora >= 0 && hora < 6) {
      this.saudacao = 'Trabalhando tarde da noite, Coordenador?';
    } else if (hora >= 6 && hora < 12) {
      this.saudacao = 'Bom Dia, Coordenador';
    } else if (hora >= 12 && hora < 18) {
      this.saudacao = 'Boa Tarde, Coordenador';
    } else {
      this.saudacao = 'Boa Noite, Coordenador';
    }
  }
}
