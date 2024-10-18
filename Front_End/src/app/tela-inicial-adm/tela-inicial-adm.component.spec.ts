import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelaInicialAdmComponent } from './tela-inicial-adm.component';

describe('TelaInicialAdmComponent', () => {
  let component: TelaInicialAdmComponent;
  let fixture: ComponentFixture<TelaInicialAdmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TelaInicialAdmComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TelaInicialAdmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
