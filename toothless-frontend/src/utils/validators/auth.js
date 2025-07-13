export const emailRules = [
    (v) => !!v || 'Email address is required field.',
    (v) => (v && v.length >= 6 && v.length <= 60) || 'Email address must be between 6 and 60 characters long.',
    (v) => /.+@.+\..+/.test(v) || 'Email address is not valid.'
];

export const passwordRules = [
    (v) => !!v || 'Password is required field.',
    (v) => (v && v.length >= 8) || 'Password must contain at least 8 characters long.',
    (v) => /^[a-zA-Z0-9!@#$%^&*()_+]+$/.test(v) || 'The password must contain only numbers, Latin uppercase or lowercase letters and special characters.',
    (v) => /[A-Z]/.test(v) || 'Password must contain at least one uppercase letter.',
    (v) => /[a-z]/.test(v) || 'Password must contain at least one lowercase letter.',
    (v) => /\d/.test(v) || 'Password must contain at least one digit.',
    (v) => /[@$!%*?&]/.test(v) || 'Password must contain at least one special character (@$!%*?&).'
];

export const confirmPasswordRules = [
    (v) => !!v || 'Password is required field.',
];

export const nameRules = [
    (v) => !!v || 'This field is required field.',
    (v) => (v && v.length >= 2 ) || 'This field must contain at least 2 characters long.',
    (v) => (v && v.length <= 50) || 'This field must contain no more than 50 characters long.'
];

export const middleNameRules = [
    (v) => !!v || '',
    (v) => (v  && v.length >= 2) || 'This field must contain at least 2 characters long.',
    (v) => (v && v.length <= 50) || 'This field must contain no more than 50 characters long.'
];

export const phoneNumberRules = [
    (v) => !!v || 'Phone number is required field.',
    (v) => /^\+7 \(\d{3}\) \d{3}-\d{2}-\d{2}$/.test(v) || 'Phone number format: +7 (XXX) XXX-XX-XX.'
];

export const birthDateRules = [
    (v) => !!v || 'Date of birth is required field.',
    (v) => new Date(v) < new Date() || 'Date of birth must be from the past.',
    (v) => new Date(v).getFullYear() > 1905 || 'Your age cannot be more than that of the longest-lived person in the world.'
];

export const verificationCodeRules = [
    (v) => !!v || 'Access code is required field for registration as dentist.',
    (v) => !/[^0-9]/.test(v) || 'Access code must not contain letters or special characters.',
    (v) => (v && v.length >= 6) || 'Access code length must be equals 6 digits.'
];