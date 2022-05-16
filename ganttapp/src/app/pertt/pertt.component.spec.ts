import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerttComponent } from './pertt.component';

describe('PerttComponent', () => {
  let component: PerttComponent;
  let fixture: ComponentFixture<PerttComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerttComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerttComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
