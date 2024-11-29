import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeminavbarComponent } from './seminavbar.component';

describe('SeminavbarComponent', () => {
  let component: SeminavbarComponent;
  let fixture: ComponentFixture<SeminavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeminavbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SeminavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
