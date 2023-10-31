from manim import *

# class MovingFrame(Scene):
#     def construct(self):
#         equation = MathTex("ax^3", "+bx^2", "+cx+d", "=\ 0", font_size=96)

#         equation.set_color(BLACK)

#         self.play(Write(equation))
#         self.wait()

# class MovingFrame2(Scene):
#     def construct(self):
#         equation = MathTex(r"&1.\ ax^2 = bx    &2.\ ax^2 = c \\ &3.\ ax = c    &4.\ ax^2+bx = c \\ &5.\ ax^2+c = bx  &6.\ bx+c = ax^2", font_size=60)

#         equation.set_color(BLACK)

#         self.play(Write(equation))    
#         self.wait(3)

# class MovingFrame3(Scene):
#     def construct(self):
#         equation0 = MathTex(r"&1.\ x^3=c   \\ &4.\ x^3+ax^2=bx \\ &7.\ x^3+bx=c \\ &10.\ x^3+ax^2=c \\ &13.\ x^3+ax^2+bx=c \\ &16.\ x^3=ax^2+bx+c \\ &19.\ x^3+c=ax^2+bx")

#         equation0.set_color(BLACK)

#         self.play(Write(equation0)) 
#         self.wait(3)

# class MovingFrame4(Scene):
#     def construct(self):
#         equation1 = MathTex(r"&2.\ x^3=bx  \\ &5.\ x^3+bx=ax^2 \\ &8.\ x^3+c=bx \\ &11.\ x^3+c=ax^2 \\ &14.\ x^3+ax^2+c=bx \\ &17.\ x^3+ax^2=bx+c")

#         equation1.set_color(BLACK)

#         self.wait(1)
#         self.play(Write(equation1))    
#         self.wait(2)

# class MovingFrame5(Scene):
#     def construct(self):
#         equation2 = MathTex(r"&3.\ x^3=ax^2\\ &6.\ x^3=ax^2+bx \\ &9.\ x^3=bx+c \\ &12.\ x^3=ax^2+c \\ &15.\ x^3+bx+c=ax^2 \\ &18.\ x^3+bx=ax^2+c")

#         equation2.set_color(BLACK)

#         self.wait(2)
#         self.play(Write(equation2))    
#         self.wait(1)

class MovingFrame6(Scene):
    def construct(self):
        equation = MathTex("ax^3", "+bx^2", "+cx+d", "=\ 0", font_size=96)
        equation.save_state()
        equation1 = MathTex("ax^3",         "+cx+d", "=\ 0", font_size=96)
        
        equation.set_color(BLACK)
        equation1.set_color(BLACK)

        self.play(Write(equation))
        self.play(FadeOut(equation))
        self.play(FadeIn(equation1))
        self.play(FadeOut(equation1))

        self.wait()