package basis.challenge.ui.createorupdateuser

interface CreateOrUpdateUserResult {
    data object UserUpdatedWithSuccess : CreateOrUpdateUserResult

    data object UserCreatedWithSuccess : CreateOrUpdateUserResult
}
