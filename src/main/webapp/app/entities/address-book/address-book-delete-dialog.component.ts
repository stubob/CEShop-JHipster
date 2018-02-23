import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AddressBook } from './address-book.model';
import { AddressBookPopupService } from './address-book-popup.service';
import { AddressBookService } from './address-book.service';

@Component({
    selector: 'jhi-address-book-delete-dialog',
    templateUrl: './address-book-delete-dialog.component.html'
})
export class AddressBookDeleteDialogComponent {

    addressBook: AddressBook;

    constructor(
        private addressBookService: AddressBookService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressBookService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'addressBookListModification',
                content: 'Deleted an addressBook'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-address-book-delete-popup',
    template: ''
})
export class AddressBookDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressBookPopupService: AddressBookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.addressBookPopupService
                .open(AddressBookDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
