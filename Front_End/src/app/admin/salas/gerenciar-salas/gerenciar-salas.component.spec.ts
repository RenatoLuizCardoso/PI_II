import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarSalasComponent } from './gerenciar-salas.component';

describe('GerenciarSalasComponent', () => {
  let component: GerenciarSalasComponent;
  let fixture: ComponentFixture<GerenciarSalasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GerenciarSalasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GerenciarSalasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
