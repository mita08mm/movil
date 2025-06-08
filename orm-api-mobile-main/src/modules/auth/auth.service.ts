import { Injectable, UnauthorizedException, ConflictException } from '@nestjs/common';
import { UserService } from '../../model/user/user.service';
import { LoginAuthDto } from './dto/login-auth.dto';
import { RegisterAuthDto } from './dto/register-auth.dto';
import * as bcrypt from 'bcryptjs';
import {sign} from 'jsonwebtoken';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class AuthService {
  constructor(
    private userService: UserService,
    private configService: ConfigService,
  ) {}

  async register(registerDto: RegisterAuthDto): Promise<{ message: string }> {
    const saltRounds = 10;
    const hashedPassword = await bcrypt.hash(registerDto.password, saltRounds);

    const existingUser = await this.userService.findOneByEmail(registerDto.email);

    if (existingUser) {
      throw new ConflictException('Email already exists');
    }

    await this.userService.create(registerDto, hashedPassword);
    return { message: 'User registered successfully' };
  }

  async login(loginDto: LoginAuthDto): Promise<{ accessToken: string }> {
    const user = await this.userService.findOneByEmail(loginDto.email);

    if (!user) {
      throw new UnauthorizedException('Invalid credentials');
    }

    const isPasswordMatching = await bcrypt.compare(
      loginDto.password,
      user.passwordHash,
    );

    if (!isPasswordMatching) {
      throw new UnauthorizedException('Invalid credentials');
    }

    const payload = { email: user.email, sub: user.id };
    const secret = this.configService.get<string>('JWT_SECRET');

    if (!secret) {
      throw new Error('JWT_SECRET not configured in environment variables');
    }
    const accessToken = sign(payload, secret, { expiresIn: '1w' });

    return { accessToken };
  }

  // Password recovery logic will go here later
}
