import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservarComponent } from './reservar.component';

describe('ReservarComponent', () => {
  let component: ReservarComponent;
  let fixture: ComponentFixture<ReservarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReservarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
