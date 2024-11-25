import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CsalasService } from '../../../serv/admin/csalas.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-editar-salas',
  templateUrl: './editar-salas.component.html',
  styleUrls: ['./editar-salas.component.css']
})
export class EditarSalasComponent implements OnInit {
  salaForm: FormGroup;
  registerError: boolean = false;
  mensagemSucesso: boolean = false;
  todosRecursos: string[] = ['Computadores', 'Projetor', 'Ventiladores']; // Recursos disponíveis

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private csalasService: CsalasService,
    private titleService: Title
  ) {
    this.salaForm = this.fb.group({
      roomId: [''],
      roomType: ['', Validators.required],
      roomCapacity: ['', [Validators.required, Validators.min(1)]],
      roomFloor: ['', [Validators.required, Validators.min(0)]],
      roomResources: this.fb.array([], Validators.required), // Recursos como FormArray
      roomAvailability: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.titleService.setTitle('Edição de Salas');
    this.carregarSala();
  }

  get f() {
    return this.salaForm.controls;
  }

  get resources(): FormArray {
    return this.salaForm.get('roomResources') as FormArray;
  }

  carregarSala(): void {
    const id = this.route.snapshot.paramMap.get('id'); // Pega o ID da URL

    if (id) {
      this.csalasService.getSalaById(id).subscribe(
        data => {
          this.salaForm.patchValue({
            roomId: data.roomId,
            roomType: data.roomType,
            roomCapacity: data.roomCapacity,
            roomFloor: data.roomFloor,
            roomAvailability: data.roomAvailability
          });

          // Converte os recursos recebidos para o FormArray
          const recursosSelecionados: string[] = data.roomResources.split(', '); // Explicitamente definido como string[]
          recursosSelecionados.forEach((recurso: string) => { // Tipo adicionado aqui
            if (this.todosRecursos.includes(recurso)) {
              this.resources.push(this.fb.control(recurso));
            }
          });
        },
        error => console.error('Erro ao carregar sala: ', error)
      );
    } else {
      console.error('ID não encontrado na rota.');
    }
  }


  isRecursoSelecionado(recurso: string): boolean {
    return this.resources.value.includes(recurso);
  }

  onResourceChange(event: any, recurso: string): void {
    const checked = event.target.checked;
    if (checked) {
      this.resources.push(this.fb.control(recurso));
    } else {
      const index = this.resources.controls.findIndex(control => control.value === recurso);
      if (index !== -1) {
        this.resources.removeAt(index);
      }
    }
  }

  salvar(): void {
    if (this.salaForm.valid) {
      const salaAtualizada = {
        ...this.salaForm.value,
        roomResources: this.resources.value.join(', ') // Converte FormArray para string
      };

      console.log('JSON enviado para atualização:', JSON.stringify(salaAtualizada));

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
}
