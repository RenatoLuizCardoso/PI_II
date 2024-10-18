import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarDisciplinasComponent } from './editar-disciplinas.component';

describe('EditarDisciplinasComponent', () => {
  let component: EditarDisciplinasComponent;
  let fixture: ComponentFixture<EditarDisciplinasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditarDisciplinasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarDisciplinasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
