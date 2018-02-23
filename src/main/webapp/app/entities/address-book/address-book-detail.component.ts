import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AddressBook } from './address-book.model';
import { AddressBookService } from './address-book.service';

@Component({
    selector: 'jhi-address-book-detail',
    templateUrl: './address-book-detail.component.html'
})
export class AddressBookDetailComponent implements OnInit, OnDestroy {

    addressBook: AddressBook;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private addressBookService: AddressBookService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAddressBooks();
    }

    load(id) {
        this.addressBookService.find(id)
            .subscribe((addressBookResponse: HttpResponse<AddressBook>) => {
                this.addressBook = addressBookResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAddressBooks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'addressBookListModification',
            (response) => this.load(this.addressBook.id)
        );
    }
}
