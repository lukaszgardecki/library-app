import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { ModalService } from '../../../core/services/modal.service';

@Component({
  selector: 'app-modal-host',
  imports: [],
  templateUrl: './modal-host.component.html',
  styleUrl: './modal-host.component.css'
})
export class ModalHostComponent {
  @ViewChild('modalHost', { read: ViewContainerRef, static: true })
  viewContainerRef!: ViewContainerRef;

  constructor(public modalService: ModalService) {}

  ngAfterViewInit() {
    this.modalService.registerViewContainer(this.viewContainerRef);
  }
}
