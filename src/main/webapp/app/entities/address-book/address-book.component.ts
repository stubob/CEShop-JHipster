import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AddressBook } from './address-book.model';
import { AddressBookService } from './address-book.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-address-book',
    templateUrl: './address-book.component.html'
})
export class AddressBookComponent implements OnInit, OnDestroy {
addressBooks: AddressBook[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private addressBookService: AddressBookService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.addressBookService.query().subscribe(
            (res: HttpResponse<AddressBook[]>) => {
                this.addressBooks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAddressBooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AddressBook) {
        return item.id;
    }
    registerChangeInAddressBooks() {
        this.eventSubscriber = this.eventManager.subscribe('addressBookListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
