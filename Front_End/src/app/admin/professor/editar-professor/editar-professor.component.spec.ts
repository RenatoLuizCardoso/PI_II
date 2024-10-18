import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarProfessorComponent } from './editar-professor.component';

describe('EditarProfessorComponent', () => {
  let component: EditarProfessorComponent;
  let fixture: ComponentFixture<EditarProfessorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditarProfessorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarProfessorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
