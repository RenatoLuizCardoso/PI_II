import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroHorarioFixoComponent } from './cadastro-horario-fixo.component';

describe('CadastroHorarioFixoComponent', () => {
  let component: CadastroHorarioFixoComponent;
  let fixture: ComponentFixture<CadastroHorarioFixoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CadastroHorarioFixoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadastroHorarioFixoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
