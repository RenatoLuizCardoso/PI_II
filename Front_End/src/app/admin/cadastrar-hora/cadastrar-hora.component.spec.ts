import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarHoraComponent } from './cadastrar-hora.component';

describe('CadastrarHoraComponent', () => {
  let component: CadastrarHoraComponent;
  let fixture: ComponentFixture<CadastrarHoraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CadastrarHoraComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadastrarHoraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
