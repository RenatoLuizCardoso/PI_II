import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CsalasService } from '../../../serv/admin/csalas.service';

@Component({
  selector: 'app-editar-salas',
  templateUrl: './editar-salas.component.html',
  styleUrls: ['./editar-salas.component.css']
})
export class EditarSalasComponent implements OnInit {
  salaForm: FormGroup;
  registerError: boolean = false;
  mensagemSucesso: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private csalasService: CsalasService
  ) {
    // Altere o nome do campo de 'id' para 'roomId'
    this.salaForm = this.fb.group({
      roomId: [''],  // Altere 'id' para 'roomId'
      roomType: ['', Validators.required],
      roomCapacity: ['', [Validators.required, Validators.min(1), Validators.pattern('^[0-9]+$')]],
      roomFloor: ['', [Validators.required, Validators.min(0)]],
      roomResources: ['', Validators.required],
      roomAvailability: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.carregarSala();
  }

  carregarSala() {
    const id = this.route.snapshot.paramMap.get('id');  // Pegue o id da URL

    if (id) {
      this.csalasService.getSalaById(id).subscribe(
        data => {
          // Aqui, estamos agora preenchendo o campo 'roomId'
          this.salaForm.get('roomId')?.setValue(data.roomId);  // Defina o roomId
          this.salaForm.patchValue(data);  // Preenche os outros campos
        },
        error => {
          console.error('Erro ao carregar sala: ', error);
        }
      );
    } else {
      console.error('ID não encontrado na rota.');
    }
  }

  salvar() {
    if (this.salaForm.valid) {
      const salaAtualizada = this.salaForm.getRawValue();

      // Exibe o objeto que será enviado
      console.log('JSON enviado para atualização:', JSON.stringify(salaAtualizada));

      // Verifique se o roomId está correto
      if (!salaAtualizada.roomId) {
        console.error("ID da sala não encontrado!");
        return;
      }

      this.csalasService.updateSala(salaAtualizada).subscribe(
        () => {
          this.mensagemSucesso = true;
          setTimeout(() => {
            this.mensagemSucesso = false;
            this.router.navigate(['/admin/gerenciar_sala']);
          }, 3000);
        },
        error => {
          console.error('Erro ao atualizar sala: ', error);
          this.registerError = true;
        }
      );
    } else {
      this.registerError = true;
    }
  }

  get f() { return this.salaForm.controls; }
}
