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
    this.salaForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      type: ['', Validators.required],
      capacity: ['', [Validators.required, Validators.min(1), Validators.pattern('^[0-9]+$')]],
      floor: ['', [Validators.required, Validators.min(0)]],
      resources: ['', Validators.required],
      availability: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.carregarSala();
  }

  carregarSala() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.csalasService.getSalaById(id).subscribe(
        data => {
          this.salaForm.patchValue(data);
        },
        error => {
          console.error('Erro ao carregar sala: ', error);
        }
      );
    }
  }

  salvar() {
    if (this.salaForm.valid) {
      const salaAtualizada = this.salaForm.getRawValue();
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
