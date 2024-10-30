import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlideHomePageComponent } from './slide-home-page.component';

describe('SlideHomePageComponent', () => {
  let component: SlideHomePageComponent;
  let fixture: ComponentFixture<SlideHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SlideHomePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SlideHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
