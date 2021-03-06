import { Component, Input, OnInit, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CitizenRequest, Citizen, Company, User } from '../_models';
import { AuthenticationService, CitizenService, RequestService, AlertService } from '../_services';
import { Observable } from 'rxjs/Observable';


@Component({
    selector: 'request-detail',
    templateUrl: 'request-detail.component.html'
})

export class RequestDetailComponent implements OnChanges, OnInit {
    @Input()
    request: CitizenRequest;
    @Input()
    displayEmployeeActions: boolean;
    @Output()
    requestUpdated = new EventEmitter<void>();

    company: Company;
    currentUser: User;
    citizen: Citizen;
    selectedAction = 'accept';
    possibleActions = ['accept', 'reject', 'requestModification'];
    comment: string;

    constructor(private authService: AuthenticationService, private citizenService: CitizenService,
                private requestService: RequestService, private alertService: AlertService) {}

    ngOnInit() {
        this.company = this.authService.getCurrentCompany();
        this.currentUser = this.authService.getCurrentUser();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes['request']) {
            this.citizen = this.request.citizen;
        }
    }


    performAction(): void {
        this.requestService.performAction(this.request, this.selectedAction, {comment: this.comment}).subscribe(success => {
          if (success) {
            this.alertService.success('Action effectuée !');
            this.requestUpdated.emit();
            // reset fields
            this.selectedAction = 'accept';
            this.comment = null;
          } else {
            this.alertService.error('Action échouée !');
          }
        });
    }
}
