import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    AddressBookService,
    AddressBookPopupService,
    AddressBookComponent,
    AddressBookDetailComponent,
    AddressBookDialogComponent,
    AddressBookPopupComponent,
    AddressBookDeletePopupComponent,
    AddressBookDeleteDialogComponent,
    addressBookRoute,
    addressBookPopupRoute,
} from './';

const ENTITY_STATES = [
    ...addressBookRoute,
    ...addressBookPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AddressBookComponent,
        AddressBookDetailComponent,
        AddressBookDialogComponent,
        AddressBookDeleteDialogComponent,
        AddressBookPopupComponent,
        AddressBookDeletePopupComponent,
    ],
    entryComponents: [
        AddressBookComponent,
        AddressBookDialogComponent,
        AddressBookPopupComponent,
        AddressBookDeleteDialogComponent,
        AddressBookDeletePopupComponent,
    ],
    providers: [
        AddressBookService,
        AddressBookPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationAddressBookModule {}
