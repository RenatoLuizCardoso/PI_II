import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarProfComponent } from './cadastrar-prof.component';

describe('CadastrarProfComponent', () => {
  let component: CadastrarProfComponent;
  let fixture: ComponentFixture<CadastrarProfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CadastrarProfComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadastrarProfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
