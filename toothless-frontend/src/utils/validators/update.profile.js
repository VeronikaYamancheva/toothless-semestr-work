export const passwordRules = [
    (v) => (v && v.length >= 8) || 'Password must contain at least 8 characters long.',
    (v) => /^[a-zA-Z0-9!@#$%^&*()_+]+$/.test(v) || 'The password must contain only numbers, Latin uppercase or lowercase letters and special characters.',
    (v) => /[A-Z]/.test(v) || 'Password must contain at least one uppercase letter.',
    (v) => /[a-z]/.test(v) || 'Password must contain at least one lowercase letter.',
    (v) => /\d/.test(v) || 'Password must contain at least one digit.',
    (v) => /[@$!%*?&]/.test(v) || 'Password must contain at least one special character (@$!%*?&).'
];

export const nameRules = [
    (v) => !!v || 'This field is required field.',
    (v) => (v  && v.length >= 2) || 'This field must contain at least 2 characters long.',
    (v) => (v && v.length <= 50) || 'This field must contain no more than 50 characters long.'
];

export const phoneRules = [
    (v) => !!v || 'This field is required field.',
    (v) => /^\+7 \(\d{3}\) \d{3}-\d{2}-\d{2}$/.test(v) || 'Phone number format: +7 (XXX) XXX-XX-XX.'
];

export const passportNumberRules = [
    (v) => /^\d{4} \d{6}$/.test(v) || 'Passport number format: XXXX XXXXXX'
];

export const issueDateRules = [
    (v) => new Date(v) < new Date() || 'Date of issue must be from the past.',
    (v) => /^\d{2}\.\d{2}\.\d{4}$/.test(v) || 'Passport number format: XX.XX.XXXX',
    (v) => new Date(v).getFullYear() > 1905 || 'Your passport issue date cannot be more than that of the longest-lived person in the world.'
];

export const birthDateRules = [
    (v) => !!v || 'Date of birth is required field.',
    (v) => new Date (v) < new Date() || 'Date of birth must be from the past.',
    (v) => new Date(v).getFullYear() > 1905 || 'Your age cannot be more than that of the longest-lived person in the world.'
];

export const divisionCodeRules = [
    (v) => /^\d{3}-\d{3}$/.test(v) || 'Division code format: XXX-XXX'
];

export const policyNumberRules = [
    (v) => /^\d{4} \d{4} \d{4} \d{4}$/.test(v) || 'Policy number format: XXXX XXXX XXXX XXXX'
];

export const snilsNumberRules = [
    (v) => /^\d{3}-\d{3}-\d{3} \d{2}$/.test(v) || 'SNILS format: XXX-XXX-XXX XX'
];
