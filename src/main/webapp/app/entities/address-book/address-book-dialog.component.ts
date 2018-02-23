import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AddressBook } from './address-book.model';
import { AddressBookPopupService } from './address-book-popup.service';
import { AddressBookService } from './address-book.service';

@Component({
    selector: 'jhi-address-book-dialog',
    templateUrl: './address-book-dialog.component.html'
})
export class AddressBookDialogComponent implements OnInit {

    addressBook: AddressBook;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private addressBookService: AddressBookService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.addressBook.id !== undefined) {
            this.subscribeToSaveResponse(
                this.addressBookService.update(this.addressBook));
        } else {
            this.subscribeToSaveResponse(
                this.addressBookService.create(this.addressBook));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AddressBook>>) {
        result.subscribe((res: HttpResponse<AddressBook>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AddressBook) {
        this.eventManager.broadcast({ name: 'addressBookListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-address-book-popup',
    template: ''
})
export class AddressBookPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressBookPopupService: AddressBookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.addressBookPopupService
                    .open(AddressBookDialogComponent as Component, params['id']);
            } else {
                this.addressBookPopupService
                    .open(AddressBookDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
