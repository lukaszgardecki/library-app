import { Injectable } from '@angular/core';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

  public saveAsPDF(data: any, fileName: string): void {
    if (data) {
      html2canvas(data).then(canvas => {
        const margin = 25;
        const imgWidth = 210 - 2 * margin;
        const imgHeight = canvas.height * imgWidth / canvas.width;
        const contentDataURL = canvas.toDataURL('image/png');
        let pdf = new jsPDF('p', 'mm', 'a4');
        const position = margin;
        pdf.addImage(contentDataURL, 'PNG', margin, position, imgWidth, imgHeight);
        pdf.save(`${fileName}.pdf`);
      });
    }
  }
}
