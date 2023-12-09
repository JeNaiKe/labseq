import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OnInit, ViewChild, ElementRef } from '@angular/core';

import { LabseqService } from './service/labseq/labseq.service';
import { throwError } from 'rxjs';




@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  @ViewChild('nIn') nIn: ElementRef | undefined;

  public n: number = 0;
  public result: String | BigInt= "";

  constructor(private LabseqService: LabseqService) { }

  onSubmit() {
    this.n = this.nIn?.nativeElement.value;
    this.LabseqService.getValue(this.n).subscribe({
      next: (v: any) => {
        this.result = v;
      },
      error: (err: any) => { this.result = err; },
    });
  }

}
