import { BaseEntity } from './../../shared';

export class AddressBook implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public userId?: string,
        public entries?: BaseEntity[],
    ) {
    }
}
