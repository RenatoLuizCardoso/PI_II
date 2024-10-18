import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarSalaComponent } from './cadastrar-sala.component';

describe('CadastrarSalaComponent', () => {
  let component: CadastrarSalaComponent;
  let fixture: ComponentFixture<CadastrarSalaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CadastrarSalaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadastrarSalaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
