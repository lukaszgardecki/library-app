import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Rack, Shelf } from '../../../../shared/models/rack';
import { debounceTime, distinctUntilChanged } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  subscribeSearchField(control: FormControl, callback: (value: any) => void) {
    control.valueChanges.pipe(
              debounceTime(500),
              distinctUntilChanged()
          ).subscribe(value => {
            callback(value);
          });
  }

  createNewRackForm(): FormGroup {
    return new FormGroup({
      name: new FormControl('', Validators.required)
    });
  }

  createNewShelfForm(): FormGroup {
    return new FormGroup({
      name: new FormControl('', Validators.required),
    });
  }

  createEditRackForm(rack?: Rack): FormGroup {
    return new FormGroup({
      name: new FormControl(rack?.name || '', Validators.required)
    });
  }

  createEditShelfForm(shelf?: Shelf): FormGroup {
    return new FormGroup({
      name: new FormControl(shelf?.name || '', Validators.required),
    });
  }

  createMoveItemForm(rackIdInit: number, shelfIdInit: number) {
    return new FormGroup({
      rack: new FormControl(rackIdInit, Validators.required),
      shelf: new FormControl(shelfIdInit, Validators.required)
    });
  }
}
