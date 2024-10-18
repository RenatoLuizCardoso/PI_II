import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarSalasComponent } from './editar-salas.component';

describe('EditarSalaComponent', () => {
  let component: EditarSalasComponent;
  let fixture: ComponentFixture<EditarSalasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditarSalasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarSalasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
